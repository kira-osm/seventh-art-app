const { check, validationResult } = require('express-validator');

exports.validateActor = [
  check('firstName', 'Le prénom est obligatoire').notEmpty(),
  check('lastName', 'Le nom de famille est obligatoire').notEmpty(),
  check('image', 'L\'image est obligatoire').notEmpty(),
  check('age', 'L\'âge est obligatoire').notEmpty(),
  check('gender', 'Le genre est obligatoire').notEmpty(),
  check('biography', 'La biographie est obligatoire').notEmpty(),
  check('birthday', 'La date de naissance est obligatoire').notEmpty(),
  check('placeOfBirth', 'Le lieu de naissance est obligatoire').notEmpty(),
];
