# Opis reguł gry
Gra toczy się na nieskończonej planszy (płaszczyźnie) podzielonej na kwadratowe komórki. Każda komórka ma ośmiu „sąsiadów” (tzw. sąsiedztwo Moore'a), czyli komórki przylegające do niej bokami i rogami. Każda komórka może znajdować się w jednym z dwóch stanów: może być albo "żywa" (włączona), albo "martwa" (wyłączona). Stany komórek zmieniają się w pewnych jednostkach czasu. Stan wszystkich komórek w pewnej jednostce czasu jest używany do obliczenia stanu wszystkich komórek w następnej jednostce. Po obliczeniu wszystkie komórki zmieniają swój stan dokładnie w tym samym momencie. Stan komórki zależy tylko od liczby jej żywych sąsiadów. W grze w życie nie ma graczy w dosłownym tego słowa znaczeniu. Udział człowieka sprowadza się jedynie do ustalenia stanu początkowego komórek.

Zdefiniowano kilka wzorców reguł generowania, najbardziej rozpowszechnione są reguły wymyślone przez Conwaya. Do nich też odnosi się podział struktur, przedstawiony w dalszej części artykułu.

## Reguły gry według Conway
- Martwa komórka, która ma dokładnie 3 żywych sąsiadów, staje się żywa w następnej jednostce czasu (rodzi się)
- Żywa komórka z 2 albo 3 żywymi sąsiadami pozostaje nadal żywa; przy innej liczbie sąsiadów umiera (z "samotności" albo "zatłoczenia").
