const nodemailer = require("nodemailer");
const xml2js = require("xml2js");
const Datauri = require("datauri/parser");
const datauri = new Datauri();

require("dotenv").config();

const sendEmail = async (
  to,
  subject,
  htmlContent,
  imageBase64,
  xmlData,
  pdfBuffer
) => {
  try {
    // Convertir l'image base64 en format de données URI
    const imageBuffer = Buffer.from(imageBase64, "base64");
    const imageUri = datauri.format(".png", imageBuffer);

    // Créer un transporteur SMTP réutilisable avec nodemailer
    const transporter = nodemailer.createTransport({
      service: "Gmail",
      auth: {
        user: process.env.EMAIL_USER,
        pass: process.env.EMAIL_PASS,
      },
    });

    // Options de l'e-mail
    const mailOptions = {
      from: process.env.EMAIL_USER,
      to,
      subject,
      html: htmlContent,
      attachments: [
        {
          filename: "actorImage.png",
          content: imageBuffer,
          cid: "actorImage",
        },
        {
          filename: "actorData.xml",
          content: xmlData,
        },
        {
          filename: "actorData.pdf",
          content: pdfBuffer,
        },
      ],
    };

    // Envoie l'e-mail
    const info = await transporter.sendMail(mailOptions);
    console.log("E-mail envoyé :", info.response);
  } catch (error) {
    console.error("Erreur lors de l'envoi de l'e-mail :", error);
  }
};

module.exports = sendEmail;
