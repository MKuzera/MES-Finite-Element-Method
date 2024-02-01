Celem projektu jest z użyciem algorytmu MES przeprowadzenie symulacji przebiegu zmian
temperatury w określonej strukturze 2d z wykorzystaniem technologii Java.

Do obliczenia tego procesu wykorzystujemy równanie Fouriera dla procesu
niestacjonarnego (nieustalonego - czyli takiego gdzie temperatura zmienia się wraz z
czasem).

![image](https://github.com/MKuzera/MES-Finite-Element-Method/assets/116084403/6d7c29a7-e17c-488e-a521-8382b9101137)

W określonej chwili czasu pochodne temperatury mogą być traktowane jako funkcje tylko
współrzędnych x,y,z. Wtedy rozwiązanie równania jest prowadzone analogicznie jak dla
procesu ustalonego, równanie możemy sprowadzić do równania:

![image](https://github.com/MKuzera/MES-Finite-Element-Method/assets/116084403/b35dccaa-4715-4610-8516-662306356db7)

MES - Metoda Elementów Skończonych - jest to metoda rozwiązywania równań
różniczkowych spotykanych w fizyce oraz w technicę. Jest jednym z podstawowych narzędzi
komputerowego wspomagania badań naukowych. Idea MES’u to to że dowolną ciągłą
wartość (w naszym przypadku temperaturę) można zamienić na model dyskretny - czyli
otrzymujemy w postaci skończonego zbioru liczb na podstawie którego można uzyskać
rozwiązanie w dowolnym miejscu obszaru. Polega to na podzieleniu obszaru obliczeniowego
na mniejsze fragmenty (elementy skończone) które są prostsze do obliczeń, które łączą się
w węzłach tworząc siatkę. Co jest ważne siatka jest skończona tzn. ma brzeg na którym
również określamy zachowanie funkcji niewiadomej (warunki brzegowe).
Możemy uzyskać rozwiązania w tych obszarach obliczeń a następnie interpolowania
rozwiązania w pozostałych punktach obszaru za pomocą funkcji bazowych. Następnie z
funkcji (macierze) określonych w pojedynczych węzłach sklejamy to w funkcje określone w
całym obszarze (globalne). Celem było zaimplementowanie własnego rozwiązania MES
które aproksymuje nam rozwiązanie problemu rzeczywistego określonego wyżej.

Wynik symulacji dla siatki 31x31

![ezgif-5-3adb423d66](https://github.com/MKuzera/MES-Finite-Element-Method/assets/116084403/20294df9-713c-48ac-82cf-bad193d627af)
