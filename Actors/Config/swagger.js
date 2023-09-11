const swaggerJsdoc = require('swagger-jsdoc');
const path = require('path'); // Ajoutez cette ligne pour importer le module path


const options = {
  swaggerDefinition: {
    openapi: '3.0.0',
    info: {
      title: 'API des acteurs',
      version: '1.0.0',
      description: 'API pour gérer les acteurs',
    },
  },
  apis: [path.resolve(__dirname, '../Routes/*.js')], // Utilisez path.resolve pour spécifier le chemin absolu
};

const specs = swaggerJsdoc(options);

module.exports = specs;
