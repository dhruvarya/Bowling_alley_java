/*

  SMTP implementation based on code by R�al Gagnon mailto:real@rgagnon.com

 */

// the below commented out segment of libraries are required for shifting Mail sending routine from sockets to JavaMail
//import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

///
import java.io.*;
import java.util.Vector;
import java.util.Iterator;
import java.net.*;
import java.awt.print.*;

public class ScoreReport {

	private String content;
	
	public ScoreReport( Bowler bowler, int[] scores, int games ) {
		String nick = bowler.getNick();
		String full = bowler.getFullName();
		Vector<Score> v = null;
		try{
			v = ScoreHistoryFile.getScores(nick);
		} catch (Exception e){System.err.println("Error: " + e);}

		assert v != null;
		Iterator<Score> scoreIt = v.iterator();
		
		content = "";
		content += "--Lucky Strike Bowling Alley Score Report--\n";
		content += "\n";
		content += "Report for " + full + ", aka \"" + nick + "\":\n";
		content += "\n";
		content += "Final scores for this session: ";
		content += scores[0];
		for (int i = 1; i < games; i++){
			content = new StringBuilder().append(content).append(", ").append(scores[i]).toString()  ;

		}
		content += ".\n";
		content += "\n";
		content += "\n";
		content += "Previous scores by date: \n";
		while (scoreIt.hasNext()){
			Score score = scoreIt.next();

			content=new StringBuilder().append(content).append("  ").append(score.getDate()).append(" - ").append(score.getScore()).append("\n").toString()  ;

		}
		content += "\n\n";
		content += "Thank you for your continuing patronage.";

	}

	public void sendEmail(String recipient) {
		try {
			Socket s = new Socket("osfmail.rit.edu", 25);
			BufferedReader in =
				new BufferedReader(
					new InputStreamReader(s.getInputStream(), "8859_1"));
			BufferedWriter out =
				new BufferedWriter(
					new OutputStreamWriter(s.getOutputStream(), "8859_1"));

			// here you are supposed to send your username
			sendln(in, out, "HELO world");
			sendln(in, out, "MAIL FROM: <mda2376@rit.edu>");
			sendln(in, out, "RCPT TO: <" + recipient + ">");
			sendln(in, out, "DATA");
			sendln(out, "Subject: Bowling Score Report ");
			sendln(out, "From: <Lucky Strikes Bowling Club>");

			sendln(out, "Content-Type: text/plain; charset=\"us-ascii\"\r\n");
			sendln(out, content + "\n\n");
			sendln(out, "\r\n");

			sendln(in, out, ".");
			sendln(in, out, "QUIT");
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Below is code segment for shifting to JavaMail routine instead of Sockets
//	public void javaMailFunction(String recipient) {
//		String to = recipient;            // sender email
//		String from = "Lucky_strike@abc.com";       // receiver email
//		String host = "127.0.0.1";                   // mail server host
//
//		Properties properties = System.getProperties();
//		properties.setProperty("mail.smtp.host", host);
//
//		Session session = Session.getDefaultInstance(properties); // default session
//
//		try {
//			MimeMessage message = new MimeMessage(session);        // email message
//			message.setFrom(new InternetAddress(from));                    // setting header fields
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			message.setSubject("Test Mail from Java Program"); // subject line
//
//			// actual mail body
//			message.setText("You can send mail from Java program by using mail API, but you need"
//					+ "couple of more JAR files e.g. smtp.jar and activation.jar");
//
//			// Send message
//			Transport.send(message);
//			System.out.println("Email Sent successfully....");
//		} catch (MessagingException mex) {
//			mex.printStackTrace();
//		}
//	}

	public void sendPrintout() {
		PrinterJob job = PrinterJob.getPrinterJob();

		PrintableText printobj = new PrintableText(content);

		job.setPrintable(printobj);

		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				System.out.println(e);
			}
		}

	}

	public void sendln(BufferedReader in, BufferedWriter out, String s) {
		try {
			out.write(s + "\r\n");
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendln(BufferedWriter out, String s) {
		try {
			out.write(s + "\r\n");
			out.flush();
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
