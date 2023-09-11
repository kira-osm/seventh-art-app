const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const connectDB = require('./Config/database');
const actorRoutes = require('./Routes/actorRoutes');
const dotenv = require('dotenv');
const swaggerUi = require('swagger-ui-express');
const swaggerDocs = require('./Config/swagger');

dotenv.config();

// Connect to MongoDB
connectDB();

app.use(bodyParser.json({ limit: '10mb' }));
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true }));

// Utilisez /api-docs comme préfixe pour la documentation Swagger
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

app.use(bodyParser.json());

// Utilisez /v1 comme préfixe pour vos routes API
app.use('/v1', actorRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is listening on port ${PORT}`);
});
