// pdfGenerator.js

const PDFDocument = require("pdfkit");

async function generatePdf(actorData) {
  return new Promise((resolve, reject) => {
    const pdfDoc = new PDFDocument();

    const margin = 50;
    const xImage = 400;
    const yImage = 50;
    const imageWidth = 150;
    const imageHeight = 150;

    pdfDoc
      .font("Helvetica-Bold")
      .fontSize(20)
      .text(`${actorData.firstName} ${actorData.lastName}`, margin, 100);

    if (actorData.image) {
      const imageBuffer = Buffer.from(actorData.image, "base64");
      pdfDoc.image(imageBuffer, xImage, yImage, {
        width: imageWidth,
        height: imageHeight,
      });
    }

    pdfDoc
      .font("Helvetica")
      .fontSize(12)
      .text(`Âge : ${actorData.age}`, margin, 300)
      .text(`Genre : ${actorData.gender}`, margin, 320)
      .text(`Date de naissance : ${actorData.birthday}`, margin, 340)
      .text(`Lieu de naissance : ${actorData.placeOfBirth}`, margin, 360)
      .text("Biographie :", margin, 380)
      .font("Helvetica-Oblique")
      .text(actorData.biography, margin, 400, { width: 350, align: "left" });

    pdfDoc.end();

    const buffers = [];
    pdfDoc.on("data", buffers.push.bind(buffers));
    pdfDoc.on("end", () => {
      const pdfBuffer = Buffer.concat(buffers);
      resolve(pdfBuffer);
    });
  });
}

module.exports = generatePdf;
