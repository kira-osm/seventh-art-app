const swaggerJsdoc = require('swagger-jsdoc');
const path = require('path');

const options = {
  swaggerDefinition: {
    openapi: '3.0.0',
    info: {
      title: 'API des acteurs',
      version: '1.0.0',
      description: 'API pour gérer les acteurs',
    },
    servers: [
      {
        url: '/v1', // Préfixe ici
      },
    ],
  },
  apis: [path.resolve(__dirname, '../Routes/*.js')],
};

const specs = swaggerJsdoc(options);

module.exports = specs;
