package auxiliar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import fonte.GerenteDeUsuarios;

/**
 * Classe usada para mandar um e-mail para um usuário cadastrado com sua senha.
 */
public class MoneySaverMail {

	private static final String MONEY_SAVER_USUARIO = "moneysaverufcg@gmail.com";
	private static final String MONEY_SAVER_SENHA = "moneysaverufcg123456";
	private static final String ASSUNTO = "Recuperação de senha.";
	private static final GerenteDeUsuarios gerente = new GerenteDeUsuarios();

	/**
	 * Método que irá receber o e-mail do usuário como parâmetro e enviá-lo sua
	 * senha.
	 * 
	 * @param emailDoUsuario
	 *            O e-mail do usuário.
	 * @throws Exception
	 *             Lança exceção caso o usuário não esteja cadastrado.
	 */
	public static void EnviarEmail(String emailDoUsuario) throws Exception {

		if (gerente.pesquisaUsuario(emailDoUsuario) == null)
			throw new Exception("Usuário não está cadastrado.");

		final String CORPO_EMAIL = String
				.format("Olá, %s\n\nSua senha é: %s\n\nEssa é uma mensagem automática, não responda para este e-mail.",
						pegaNomeUsuario(emailDoUsuario),
						pegaSenhaUsuario(emailDoUsuario));

		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", MONEY_SAVER_USUARIO);
		props.put("mail.smtp.password", MONEY_SAVER_SENHA);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session sessao = Session.getDefaultInstance(props);
		MimeMessage mensagem = new MimeMessage(sessao);

		try {
			mensagem.setFrom(new InternetAddress(MONEY_SAVER_USUARIO));
			InternetAddress endereco = new InternetAddress(emailDoUsuario);

			mensagem.addRecipient(Message.RecipientType.TO, endereco);

			mensagem.setSubject(ASSUNTO);
			mensagem.setText(CORPO_EMAIL);

			Transport transport = sessao.getTransport("smtp");
			transport.connect(host, MONEY_SAVER_USUARIO, MONEY_SAVER_SENHA);
			transport.sendMessage(mensagem, mensagem.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que irá pegar o nome do usuário a partir de seu e-mail.
	 * 
	 * @param emailDoUsuario
	 *            E-mail do usuário.
	 * @return Retorna o nome do usuário.
	 */
	private static String pegaNomeUsuario(String emailDoUsuario) {
		return gerente.pesquisaUsuario(emailDoUsuario).getNome();
	}

	/**
	 * Método que irá pegar a senha do usuário a partir de seu e-mail. Note que
	 * a senha do usuário está criptografada e só será possível acessar
	 * descriptografada caso a classe Criptografia seja usada.
	 * 
	 * @param emailDoUsuario
	 *            O e-mail do usuário.
	 * @return Retorna a senha do usuário.
	 */
	private static String pegaSenhaUsuario(String emailDoUsuario) {
		String senha = "";
		try {
			senha = Criptografia.decrypt(gerente
					.pesquisaUsuario(emailDoUsuario).getSenha());
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
		return senha;
	}
}
