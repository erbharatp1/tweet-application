package com.csipl.hrms.service.employee;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csipl.hrms.common.util.HrmsGlobalConstantUtil;
import com.csipl.hrms.model.employee.AuthorizedSignatory;
import com.csipl.hrms.service.employee.repository.AuthorizedSignatoryRepository;
import com.csipl.hrms.service.organization.StorageService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * @author Bharat
 *
 */
@Service
@Transactional
public class AuthorizedSignatoryServiceImpl implements AuthorizedSignatoryService {
	@Autowired
	private AuthorizedSignatoryRepository authorizedSignatoryRepository;
	@Autowired
	private StorageService storageService;

	@Override
	public AuthorizedSignatory save(AuthorizedSignatory authorizedSignatory, MultipartFile file, boolean fileFlag) {
		if (fileFlag == false && authorizedSignatory.getAuthorizedId() != null) {
			authorizedSignatory.setSignatureImagePath(authorizedSignatory.getSignatureImagePath());
			authorizedSignatory = authorizedSignatoryRepository.save(authorizedSignatory);
		}
		AuthorizedSignatory autho = new AuthorizedSignatory();
		String fileName = "";
		if (fileFlag) {

			fileName = file.getOriginalFilename();
			System.out.println("File with extension : " + fileName);
			String path = storageService.createFilePath(HrmsGlobalConstantUtil.DIGITAL_SIGNATURE_AUTHORIZED);
			String dbPath = path + File.separator + fileName;
			storageService.store(file, path, fileName);
			authorizedSignatory.setSignatureImagePath(dbPath);
		}
		if (authorizedSignatory.getQrCodeStatus().equals("Y")) {
			StringBuilder sb = new StringBuilder();
			sb.append(" Name- " + authorizedSignatory.getPersonName())
					.append("\n Designation- " + authorizedSignatory.getDesignationName());
			if (authorizedSignatory.getContactNo() != null) {
				sb.append("\n Contact No- " + authorizedSignatory.getContactNo());
			}
			if (authorizedSignatory.getEmailId() != null) {
				sb.append("\n Email Id- " + authorizedSignatory.getEmailId());
			}

			String path = storageService.createFilePath(HrmsGlobalConstantUtil.DIGITAL_SIGNATURE_AUTHORIZED);
			String dbPath = path + File.separator + authorizedSignatory.getPersonName()
					+ authorizedSignatory.getLetterId() + ".png";

			String rootPath = System.getProperty("catalina.home");
			authorizedSignatory.setQrCodeImagePath(dbPath);
			rootPath = rootPath + File.separator + HrmsGlobalConstantUtil.APP_BASE_FOLDER + File.separator + dbPath;

			int size = 125;
			String fileType = "png";
			File qrFile = new File(rootPath);
			try {
				createQRImage(qrFile, sb.toString(), size, fileType);
			} catch (WriterException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		  autho = authorizedSignatoryRepository.save(authorizedSignatory);
		return authorizedSignatoryRepository.save(autho);

	}

	@Override
	public AuthorizedSignatory findAuthorizedSignatoryById(Long letterId) {
		// TODO Auto-generated method stub
		return authorizedSignatoryRepository.findAuthorizedSignatoryById(letterId);
	}

	/**
	 * 
	 * @param qrFile
	 * @param qrCodeText
	 * @param size
	 * @param fileType
	 * @throws WriterException
	 * @throws IOException
	 */
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);

		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}

}
