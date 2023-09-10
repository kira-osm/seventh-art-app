// app.js

const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const connectDB = require('./Config/database'); 
const actorRoutes = require('./Routes/actorRoutes'); 
const dotenv = require('dotenv');

dotenv.config();

// Middleware body-parser avec une limite de taille de charge utile personnalisée (10 Mo)
app.use(bodyParser.json({ limit: '10mb' }));
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true }));

// Connect to MongoDB
connectDB();

app.use(bodyParser.json());

// Use actor routes
app.use('/api', actorRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is listening on port ${PORT}`);
});
