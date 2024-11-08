const express = require('express');
const fs = require('fs');
const path = require('path');
const app = express();
const PORT = 3000;

app.use(express.json()); // Middleware to parse JSON requests
app.use(express.static('public')); // Serve static files (MP3s, images, etc.)


// Load song data from JSON file
const songsFilePath = path.join(__dirname, 'songs.json');

// GET endpoint to retrieve all songs
app.get('/songs', (req, res) => {
    fs.readFile(songsFilePath, 'utf8', (err, data) => {
        if (err) {
            return res.status(500).json({ message: 'Error reading song data' });
        }
        res.json(JSON.parse(data));
    });
});

// POST endpoint to add a new song (with image and MP3 URL)
app.post('/songs', (req, res) => {
    const { name, artist, image, mp3Url } = req.body;
    const newSong = { id: Date.now(), name, artist, image, mp3Url };

    fs.readFile(songsFilePath, 'utf8', (err, data) => {
        if (err) {
            return res.status(500).json({ message: 'Error reading song data' });
        }

        const songs = JSON.parse(data);
        songs.push(newSong);

        // Save updated song data to file
        fs.writeFile(songsFilePath, JSON.stringify(songs, null, 2), (err) => {
            if (err) {
                return res.status(500).json({ message: 'Error saving song data' });
            }
            res.status(201).json(newSong);
        });
    });
});

// Serve MP3 files (Ensure the MP3s are inside the /public/songs folder)
app.get('/songs/:filename', (req, res) => {
    const { filename } = req.params;
    const filePath = path.join(__dirname, 'public', 'songs', filename);
    res.sendFile(filePath);
});

// Serve images (Ensure images are inside the /public/images folder)
app.get('/images/:imageName', (req, res) => {
    const { imageName } = req.params;
    const imagePath = path.join(__dirname, 'public', 'images', imageName);
    res.sendFile(imagePath);
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
