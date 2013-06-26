package ar.edu.itba.it.paw.services.implementations;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ar.edu.itba.it.paw.domain.publications.Comment;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.services.interfaces.EmailSender;

public class FastEmailSender implements EmailSender {

	private String username;
	private String password;
	private boolean auth;
	private boolean starttls;
	private String host;
	private Integer port;

	public FastEmailSender(String username, String password, String auth,
			String starttls, String host, String port) {
		this.username = username;
		this.password = password;
		this.auth = Boolean.valueOf(auth);
		this.starttls = Boolean.valueOf(starttls);
		this.host = host;
		this.port = Integer.valueOf(port);

	}

	public void sendEmail(Comment comment, Publication publication) {

		Thread t = new Thread(new Sender(comment, publication));
		t.start();

	}

	private class Sender implements Runnable {

		private Comment comment;
		private Publication publication;

		public Sender(Comment comment, Publication publication) {
			this.comment = comment;
			this.publication = publication;
		}

		public void run() {

			Properties props = new Properties();

			props.put("mail.smtp.auth", auth);
			props.put("mail.smtp.starttls.enable", starttls);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress
						.parse(publication.getUser().getEmail()));
				message.setSubject("Hay alguien interesado en su propiedad");

				String text = "Hay un usuario interesado en su propiedad. Sus datos son los siguientes:\n";
				text += "*Nombre: " + comment.getName() + "\n";
				text += "*Mail: " + comment.getEmail() + "\n";
				text += "*Telefono: " + comment.getPhone() + "\n";
				if (!comment.getComment().isEmpty())
					text += "El usuario ha dejado el siguiente comentario: \n"
							+ comment.getComment() + "\n";

				message.setText(text);

				Transport.send(message);

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
