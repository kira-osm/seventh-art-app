const { createLogger, transports, format } = require('winston');

// Configuration des transports
const logger = createLogger({
  transports: [
    new transports.Console({
      level: 'info',
      format: format.combine(format.colorize(), format.simple()),
    }),
    new transports.File({ filename: 'app.log', level: 'error' }),
  ],
});

module.exports = logger;
