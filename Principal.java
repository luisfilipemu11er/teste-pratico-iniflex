import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class Principal {

    public static void main(String[] args) {
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat numberFormatter = new DecimalFormat("#,##0.00");

        // 3.1 – Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, Month.OCTOBER, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, Month.MAY, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, Month.MAY, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, Month.OCTOBER, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, Month.JANUARY, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, Month.NOVEMBER, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, Month.MARCH, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, Month.JULY, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, Month.MAY, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, Month.SEPTEMBER, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 – Remover o funcionário “João” da lista
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        System.out.println("--- 3.3 LISTA DE FUNCIONÁRIOS ---");
        // 3.3 – Imprimir todos os funcionários com suas informações formatadas
        for (Funcionario f : funcionarios) {
            System.out.println("Nome: " + f.getNome() +
                               ", Data Nascimento: " + f.getDataNascimento().format(dateFormatter) +
                               ", Salário: " + numberFormatter.format(f.getSalario()) +
                               ", Função: " + f.getFuncao());
        }

        // 3.4 – Aumentar o salário dos funcionários em 10%
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario);
        }

        // 3.5 – Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n--- 3.6 FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ---");
        // 3.6 – Imprimir os funcionários, agrupados por função
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario f : entry.getValue()) {
                System.out.println("  - " + f.getNome());
            }
        }

        System.out.println("\n--- 3.7 ANIVERSARIANTES DE OUTUBRO E DEZEMBRO ---");
        // 3.7 – Imprimir os funcionários que fazem aniversário no mês 10 e 12
        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.println(f.getNome());
            }
        }
        
        System.out.println("\n--- 3.8 FUNCIONÁRIO MAIS VELHO ---");
        // 3.8 – Imprimir o funcionário com a maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade);
        }

        System.out.println("\n--- 3.9 LISTA DE FUNCIONÁRIOS EM ORDEM ALFABÉTICA ---");
        // 3.9 – Imprimir a lista de funcionários por ordem alfabética
        funcionarios.sort(Comparator.comparing(Pessoa::getNome));
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome());
        }

        System.out.println("\n--- 3.10 TOTAL DOS SALÁRIOS ---");
        // 3.10 – Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total: " + numberFormatter.format(totalSalarios));

        System.out.println("\n--- 3.11 SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ---");
        // 3.11 – Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        for (Funcionario f : funcionarios) {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        }
    }
}