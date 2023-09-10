// models/Actor.js

const mongoose = require('mongoose');

const actorSchema = new mongoose.Schema({
  firstName: String,
  lastName: String,
  image: String,
  age: Number,
  gender: String,
  biography: String,
  birthday: String,
  placeOfBirth: String,
} );

const Actor = mongoose.model('Actor', actorSchema);

module.exports = Actor;
