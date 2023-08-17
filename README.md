# SINPLe
SINPLe (Simple Interpreted Programming Language): A object-oriented implementation of a computer language in Java.

## Introduction
Syntax is similar to PHP scripting language. 


**Usage**: 
```
java -jar "sinple.jar" <filename>
```

## A quick overview example

**Input file: general_examples.synple**
```
/* 
    About Comments 
*/
echoln("Single line comments starts with #\n");
# This is a comment

echoln("Multiple lines comments are enclosed with /*   */\n");
/* 
  This a multiple line comment
*/



/* 
    printing to standard IO 
*/
println("print, println, echo and echoln can be used to print an expression");
println("Always remember to terminate a statement with a semicolon ;\n");


/* 
    Variables
*/
echoln("Variables are weekly typed. They must start with $\n");
$this_is_a_varible = "string content";
println($this_is_a_varible );
$this_is_a_varible = 10;
println($this_is_a_varible );
println('');

/* 
    Strings
*/
println('Literals are integer numbers or strings.Floating-point numbers are not supported yet.\n');
println("Strings are enclosed with ' or \"");

println("Using \" allows for variable interpolation in a string expression");
echoln("Variable value: $this_is_a_varible");

println("String concatenation operator is a . (dot)");
println('there are ' . $this_is_a_varible  .  ' types of people in this world, those who understand binary and those who dont.');

/* 
    Conditionals
*/
println("\nIf / else statement:");
$n = 7;
if ($n  % 2 == 0)
{
   println("$n is even");
}
else
{
   println("$n is odd");
}

println("\nThere's also the switch statement:\n");
$month = 3;
println("How many days are in month $month");
switch($n)
{
   case 2: println("28 days in month $month\n");
   case 4: println("30 days in month $month\n");
   case 6: println("30 days in month $month\n");
   case 9: println("30 days in month $month\n");
   default: println("31 days in month $month\n");
}


/* 
    Loops
*/
println("for statement for printing numbers from 0 to 10");
for ($i = 0; $i <= 10; $i++) {
    echo $i . " ";
}

println("\nwhile for printing numbers from 20 to 30 ");
$i = 20;
while ($i <= 30) {
  print($i . " ");
  $i++;
}

println("\n\ndo while statement for printing numbers from 10 to 0 (in reverse order)");
$i=10;
do 
{
    print($i . " ");
    $i--;
  
} while($i >= 0);


/* 
    Arrays
*/
println("\nArrays are dynamic and can be initialized with the array(a, b, c...) expression\n");
$fruits = array('apple', 'banana', 'pineapple');

println("Arrays can be printed by implicit conversion to string representation:");
println($fruits . "\n");

println("foreach(array as variable) statement can be used to loop through the elements of a arrays");
foreach($fruits as $f)
{
   println("$f is a tasty fruit.");
}

println("\narrays are initialized on a 0-index fashion:");
$fruits[1] = 'strawberry'; // change banana -> strawberry

println('so, a for statement can be used to loop through the elements: ');
for($i=0; $i<3;$i++) 
{
   echo $fruits[$i] . " ";
}
```

**Output**

```
Single line comments starts with #

Multiple lines comments are enclosed with /*   */

print, println, echo and echoln can be used to print an expression
Always remember to terminate a statement with a semicolon ;

Variables are weekly typed. They must start with $

string content
10

Literals are integer numbers or strings.Floating-point numbers are not supported yet.\n
Strings are enclosed with ' or "
Using " allows for variable interpolation in a string expression
Variable value: 10
String concatenation operator is a . (dot)
there are 10 types of people in this world, those who understand binary and those who dont.

If / else statement:
7 is odd

There's also the switch statement:

How many days are in month 3
31 days in month 3

for statement for printing numbers from 0 to 10
0 1 2 3 4 5 6 7 8 9 10 
while for printing numbers from 20 to 30 
20 21 22 23 24 25 26 27 28 29 30 

do while statement for printing numbers from 10 to 0 (in reverse order)
10 9 8 7 6 5 4 3 2 1 0 
Arrays are dynamic and can be initialized with the array(a, b, c...) expression

Arrays can be printed by implicit conversion to string representation:
[apple, banana, pineapple]

foreach(array as variable) statement can be used to loop through the elements of a arrays
apple is a tasty fruit.
banana is a tasty fruit.
pineapple is a tasty fruit.

arrays are initialized on a 0-index fashion:
so, a for statement can be used to loop through the elements: 
apple strawberry pineapple
```

## Working with functions
**Input file: factorial.synple**
```
# Example of a function with recursion
function factorial($n)
{
   if($n == 0  || $n == 1 ) 
		return 1;
   else 
		return $n * factorial($n-1);
}

echoln factorial(4);
```
**Output**

```
24
```

## Final Notes:
- This project does not have the intention to build a performatic and complete programming language. The main purpose is for learning reasons.

