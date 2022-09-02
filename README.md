# DemoProject
Console application in Java (the main class is called Main), into which it reads input data from the input.txt file:

n - число
далее n строк
m - число
далее m строк

Пример 1:

input.txt:

4
гвоздь
шуруп
краска синяя
ведро для воды
3
краска
корыто для воды
шуруп 3х1.5

ouput.txt:

гвоздь:?
шуруп:шуруп 3х1.5
краска синяя:краска
ведро для воды:корыто для воды

Пример 2:

1
Бетон с присадкой
1
Цемент

ouput.txt:

Бетон с присадкой:Цемент

Пример 3:

1
Бетон с присадкой
2
присадка для бетона
доставка

ouput.txt:

Бетон с присадкой:присадка бля бетона
доставка:?

The program must match the most similar lines from the first set with the lines from the second set (one to one) and output the result to a file output.txt.



