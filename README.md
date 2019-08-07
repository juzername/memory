# memory
NOAH’S ARK MEMORY GAME
(NOÉ BÁRKÁJA MEMÓRIAJÁTÉK)

Ez a memóriajáték az állatnevek angol tanulásához nyújt egy interaktív lehetőséget. A játék két módban használható. Az első mód (Memory) a szavak megjegyzésében segít, a második (Word Cards) pedig a már megtanult szavak gyakorlásában, az ismeretünk ellenőrzésében. A Memory mód az ismert memóriajátékot tartalmazza, amikor az ablakban megjelenő kártyák közül kell az ugyanolyanokat megtalálni. Kezdetben az összes lap lefelé fordítva van, és minden körben kettőre lehet kattintani, ekkor ez a kettő színével felfelé látható. Ha ezek megegyeznek, akkor eltűnnek. Ha nem, akkor újból eredeti helyzetbe állnak, hátlappal felfelé. Ezt folytatjuk, az egyező lapokat keresve. Ha az összes lapnak megtaláltuk a párját, akkor vége a játéknak. A kártyákon állatok rajzai láthatók, alatta pedig az angol nevük.
A Word Cards módban szókártyákkal játszhatunk. A kártyák fele csak az állatnevet tartalmazza, és a hozzájuk tartozó párok pedig csak a rajzot. Ugyanúgy a memóriajáték szabályai érvényesek, és ugyanazokkal az állatnevekkel megy a játék, csak itt nem ugyanolyan képeket keresünk, hanem „kép – angol szó” párosításokat. A cél az, hogy minél kevesebb körből a játék végéhez jussunk. Erről fájlba írható/fájlból olvasható eredménytábla készül. Ehhez a menüben szükséges megadni a játékos nevét. Ezen kívül itt lehet kiválasztani a játékmódot is, illetve azt, hogy az állatnevek melyik csoportját szeretnénk megtanulni/begyakorolni, melyik élőhelyhez kötötteket. (Jungle, Safari, River, Pet, Farm)

Use-case-ek:
A Use-case-ek aktorja a játékos, azaz aki a programunkat használja. A használati esetei:
•	Menübeállítások
o	a játékos nevének begépelése (Enterrel a végén)
o	a játéktípus kiválasztása (Memory/Word Cards)
o	állatok élőhelyének kiválasztása
•	Játék vezérlése
o	körönként 2 lap kiválasztása kattintással
o	játékból kilépés egy erre alkalmas gombra kattintással
o	új játék kérése (ekkor a program „újraosztja” a lapokat)
