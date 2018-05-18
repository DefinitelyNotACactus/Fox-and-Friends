# Fox-and-Friends
Uma simulação de um campo qualquer com quatro animais. O projeto original de BlueJ foi modificado para NetBeans, criando assim três pacotes (animal, field, simulator) além de um Launcher.

# Exercício 10.50
Foi adicionado o Ornitorrinco (Platypus.java) como presa e o Dragão de Komodo (KomodoDragon.java) como predador das Raposas e dos Ornitorrincos. Além disso, a classe Animal foi renomeada para AbstractAnimal, e foram adicionadas mais duas subclasses AbstractPredator e AbstractPrey, com isso KomodoDragon e Fox viraram subclasses de AbstractPredator, Rabbit e Platypus subclasses de AbstractPrey. Assim movemos o maior número possível de métodos para as superclasses. No mais, em simulator, fizemos modificações para serem criados Dragões e Ornitorrincos e atribuímos cores para eles (Ciano para Ornitorrinco e Vermelho para o Dragão de Komodo), para facilitar a visualização da simulação longa foi simulate possui um timer, assim, esperando um pouco antes de dar o próximo passo. Porém, o método antigo foi mantido, só que sobre outro nome (simulateWithNoTimer).

# Exercício 10.51
Para resolver este exercício, o método giveBirth de cada animal foi movido para AbstractAnimal (antigo Animal) e foi criado nessa superclasse um método abstrato chamado createAnimal que deve retornar um novo animal. (Exemplo: Se um Coelho chamar este método ele retornará um novo Coelho). 
