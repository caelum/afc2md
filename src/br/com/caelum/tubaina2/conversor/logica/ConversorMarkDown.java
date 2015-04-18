package br.com.caelum.tubaina2.conversor.logica;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeBox;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeChapter;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeCode;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeExercise;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeImg;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeIndex;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeInlineCode;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeItalico;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeLabel;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeList;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeNote;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeParagrafo;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeQuestion;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeQuote;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeRefLabel;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeSection;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeSintaxe;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeTitle;
import br.com.caelum.tubaina2.conversor.logica.sintaxe.ConversorDeTodo;
import br.com.caelum.tubaina2.conversor.modelo.AFC;
import br.com.caelum.tubaina2.conversor.modelo.MarkDown;

public class ConversorMarkDown {
	
	private List<ConversorDeSintaxe> conversores = new ArrayList<>();
	
	public ConversorMarkDown() {
		conversores.add(new ConversorDeTitle());
		conversores.add(new ConversorDeSection());
		conversores.add(new ConversorDeBox());
		conversores.add(new ConversorDeCode());
		conversores.add(new ConversorDeItalico());
		conversores.add(new ConversorDeInlineCode());
		conversores.add(new ConversorDeImg());
		conversores.add(new ConversorDeIndex());
		conversores.add(new ConversorDeLabel());
		conversores.add(new ConversorDeRefLabel());
		conversores.add(new ConversorDeList());
		conversores.add(new ConversorDeTodo());
		conversores.add(new ConversorDeNote());
		conversores.add(new ConversorDeParagrafo());
		conversores.add(new ConversorDeQuote());
		conversores.add(new ConversorDeChapter());
		conversores.add(new ConversorDeQuestion());
		conversores.add(new ConversorDeExercise());
	}
	
	public MarkDown converte(AFC afc) throws IOException {
		String conteudoAFC = afc.conteudo();
		
		String titulo = descobreTitulo(conteudoAFC);
		Path pathMD = descobrePath(afc);

		String conteudoMD = converteConteudo(conteudoAFC);
		
		return new MarkDown(pathMD, conteudoMD, titulo);
	}

	private String converteConteudo(String conteudoAFC) {
		String conteudoMD = conteudoAFC;
		for (ConversorDeSintaxe conversor : conversores) {
			conteudoMD = conversor.converte(conteudoMD);
		}
		return conteudoMD;
	}

	private String descobreTitulo(String conteudo) {
		Matcher matcher = Pattern.compile(ConversorDeChapter.REGEX_CHAPTER).matcher(conteudo);
		matcher.find();
		String titulo = matcher.group(1);
		if(titulo != null && !titulo.isEmpty()) {
			return titulo.trim();
		} 
		return "Capítulo sem título";
	}

	private Path descobrePath(AFC afc) {
		if(afc.isReadme()){
			return afc.path().getParent().resolve("README.md");
		} else {
			return Paths.get(afc.path().toString().replace(".afc", ".md"));
		}
	}

}
