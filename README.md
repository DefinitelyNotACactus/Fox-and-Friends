 a# Fox-and-Friends
Uma simulação de um campo qualquer com quatro animais. O projeto original de BlueJ foi modificado para NetBeans, criando assim três pacotes (animal, field, simulator), um launcher como executável principal, além disso, foi adicionado mais duas classes abstratas como subclasse de Animal (renomeado para AbstractAnimal): AbstractPredator, para predadores, e AbstractPrey, para presas.

Os números dos exercícios estão de acordo com a 5a Edição do Livro.

# Exercício 10.50
Na resolução desta questão, criamos uma nova presa (Ornitorrinco) com a cor ciano, e um novo predador (Dragão de Komodo) que possui como cor, o vermelho. 

O Ornintorrinco em comparação com o Coelho, possui uma chance menor de procriação, menor número máximo de filhos, além de uma idade máxima menor e uma idade mínima para procriação maior e ele tem um valor de comida menor. Ornintorrinco (Platypus) é uma subclasse de AbstractPrey (que é uma subclasse de AbstractAnimal).

Já o Dragão em comparação com a Raposa, tem chances menores de se procriar, porém, vive mais (e tem sua idade mínima para procriação maior) e pode ter mais filhos que uma raposa. Por ser um predador o dragão precisa caçar outros animais para sobreviver, assim foi editado o método findFood para fazê-lo caçar raposas e ornitorrincos. Dragão de Komodo (KomodoDragon) é uma subclasse de AbstractPredator (que é uma subclasse de AbstractAnimal).

# Exercício 10.51 
No código original, em Rabbit tinhamos o seguinte:
```
private void giveBirth(List<Animal> newRabbits)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }
```
Ignorando o nome das variáveis/parâmetros/comentários a diferença para Fox seria no tipo da variável young, no caso, Fox. Assim na superclasse, o método ficou da seguinte maneira:
```
protected void giveBirth(List<AbstractAnimal> newAnimals)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            AbstractAnimal young = createAnimal(false, field, loc);
            newAnimals.add(young);
        }
    }
```  
  Onde a varíavel young virou do tipo AbstractAnimal. Como se trata de uma classe abstrata (logo não pode ser instanciada), foi necessário criar um novo método abstrato: createAnimal que retornará um novo AbstractAnimal. Assim o método createAnimal foi implementado em suas subclasses retornando o animal que deve ser criado. (Exemplo em Platypus: )
  ```
  public AbstractAnimal createAnimal(boolean randomAge, Field field, Location location)
    {
        return new Platypus(randomAge, field, location);
    }
