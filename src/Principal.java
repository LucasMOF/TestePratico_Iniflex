import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        funcionarios.removeIf(f -> f.getNome().equals("João"));

        System.out.println("\nLista de Funcionários:");
        imprimirFuncionarios(funcionarios, dataFormatter, decimalFormat);

        for (Funcionario f : funcionarios) {
            BigDecimal aumento = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(aumento);
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionarios agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            imprimirFuncionarios(entry.getValue(), dataFormatter, decimalFormat);
        }

        System.out.println("\nAniversariantes nos mesês 10 e 12:");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " - " + dataFormatter.format(f.getDataNascimento())));

        System.out.println("\nFuncionario com a maior idade:");
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Pessoa::getDataNascimento));
        long idadeMaisVelho = ChronoUnit.YEARS.between(maisVelho.getDataNascimento(), LocalDate.now());
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idadeMaisVelho + " anos");

        System.out.println("\nFuncionarios por ordem alfabetica:");
        List<Funcionario> ordemAlfabetica = new ArrayList<>(funcionarios);
        ordemAlfabetica.sort(Comparator.comparing(Pessoa::getNome));
        imprimirFuncionarios(ordemAlfabetica, dataFormatter, decimalFormat);

        System.out.println("\nQuantidade de funcionarios por salário mínimo:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        for (Funcionario f : funcionarios) {
            BigDecimal qtdSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.DOWN);
            System.out.println(f.getNome() + " ganha " + decimalFormat.format(qtdSalariosMinimos) + " salários mínimos.");
        }
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios, DateTimeFormatter dataFormatter, DecimalFormat decimalFormat) {
        for (Funcionario f : funcionarios) {
            String dataFormatada = f.getDataNascimento().format(dataFormatter);
            String salarioFormatado = decimalFormat.format(f.getSalario());
            System.out.println("Nome: " + f.getNome() + " | Data Nascimento: " + dataFormatada +
                    " | Salário: R$ " + salarioFormatado + " | Função: " + f.getFuncao());
        }
    }
}

