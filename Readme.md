---
title: SightSeer
author: Zuletzt bearbeitet von Lukas Schauhuber
documentclass: scrartcl
classoption:
  - a4paper
header-includes: |
    \usepackage{german} 
    \usepackage[a4paper,left=2.5cm, right=2.5cm,top=2.5cm, bottom=3cm]{geometry}
    \usepackage{fancyhdr}
    \PassOptionsToPackage{hyphens}{url}\usepackage{hyperref}
    \pagestyle{fancy}
    \fancyhf{}
    \rhead{Mobile Apps für Android}
    \lhead{Übungsaufgaben}
    \cfoot{\includegraphics[height=2cm]{docs/footer.png}}
    \fancypagestyle{plain}{
      \fancyhf{}
      \rhead{Mobile Apps für Android}
      \lhead{Übungsaufgaben}
      \cfoot[C]{\includegraphics[height=2cm]{docs/footer.png}}}
      
---

# U10 | Sightseer

![Cover für die zehnte Übungsaufgabe](./docs/cover.png)

## Aufgabe

Das Ziel dieser Aufgabe ist die Implementierung einer App zur Navigation am Campus der Universität Regensburg. Dazu soll der aktuelle Standpunkt des Smartphones mit den Positionsdaten einiger vorgegebener Orte in Verbindung gesetzt werden, um die Länge und die Zielrichtung der Route zu ermitteln.

## Hinweise

* Zum Abfragen der notwendigen Permissions kann der Context verwendet werden. Tragen Sie diese auch im Manifest ein
  * `Permissions`: https://developer.android.com/training/permissions/requesting
* Beschäftigen Sie sich in dieser Aufgabe der Einfachheit halber nicht damit, was passieren soll wenn der Nutzer die Berechtigung ablehnt
* Verwenden Sie Für die Abfrage und Verarbeitung der Positionsdaten die Vorlesung (VL9), sowie die Android-Dokumentation:
  * `Location`: https://developer.android.com/reference/android/location/Location
  * `LocationManager`: https://developer.android.com/reference/android/location/LocationManager
* Sie können die Location-Funktion der erweiterten Features des Android Emulators verwenden, um die App von zu Hause zu testen.

## Ausgangslage
* Die Layouts für die `LocationListActivity`, die `NavigationActivity` sowie das benötigte `location_list_item` für den Adapter sind bereits vollständig
* Die `Destination`-Klasse repräsentiert die Kartendaten zu bestimmten Orten auf dem Campus. Die `NavigationDetail`-Klasse dürfen Sie dagegen als eine Art Wegbeschreibung von einer Location zu einem bestimmten Ziel verstehen
* Alle benötigten Klassen sind bereits erstellt. Machen Sie sich mit der Struktur vertraut und vervollständigen Sie die `NavigationController`-Klasse mit den notwendigen Methoden. Erstellen Sie Instanzvariablen wenn nötig. Integrieren Sie diesen zum Abschluss in die Activity

## Vorgehen
### Anfragen der Berechtigungen 
Stellen Sie sicher dass die App über die notwendigen Berechtigungen verfügt. SightSeer muss in der Lage sein die `FINE_LOCATION` des Geräts abzufragen. Anders als beim ebenfalls erforderlichen Internetzugriff handelt es sich dabei um eine sensitivere Permission. Diese muss zum Startzeitpunkt der Anwendung geprüft und angefragt werden.

Starten Sie die App an dieser Stelle auf dem Emulator, um zu testen, ob das Popup für die Berechtigung, wie auf dem Screenshot sichtbar, korrekt angezeigt wird.
**Hinweis:** Es wird empfohlen die Berechtigungen zusätzlich immer dann zu prüfen, wenn die entsprechende Funktion verwendet wird (auch im `NavigationController`). Dazu kann die `ActivityCompat`-Klasse verwendet werden. Prüfen Sie zusätzlich zur `FINE_LOCATION` auch die `COARSE_LOCATION`.

### Auslesen der aktuellen Position
Kümmern Sie sich als nächstes darum, die Positionsdaten des Geräts auszulesen. Dafür benötigen Sie in der `NavigationController`-Klasse eine Instanzvariable vom Typ `LocationManager`.
Mithilfe eines `Criteria`-Objekts können Sie durch Setzen bestimmter Vorraussetzungen den passenden Provider ermitteln. (Beispielsweise GPS, Mobile Daten, WIFI).
Hier verwenden wir folgende Eigenschaften:

* Genauigkeit: `ACCURACY_FINE`
* Leistungsbedarf: `POWER_MEDIUM`
* Ausrichtung erforderlich: `true`

Mit diesem Provider kann nun die aktuelle Position über den LocationManager ausgelesen werden.

### Festlegen einer Ziellocation
Sorgen Sie dafür, dass dem `NavigationController` eine Instanz von `Destination` übergeben werden kann. Aus dieser soll, abhängig von der aktuellen Position, ein `NavigationDetail`-Objekt erzeugt und über das `NavigationListener`-Interface an die entsprechende Activity übergeben werden.

Um den Richtungswert (bearing) zu erhalten, muss die Differenz aus dem Richtungswert zum Zielpunkt und dem an der aktuellen Position gebildet werden. Die Location-Klasse bietet hier passende Methoden.

### Navigationsfunktionalität
Implementieren Sie an dieser Stelle die Navigationsfunktionalität der App, indem Sie dem `LocationManager` mitteilen, dass der `NavigationController` in einem bestimmten Intervall über Positionsupdates informiert werden will.
In der Callback-Methode `onLocationChanged` erhalten Sie dann regelmäßig die neuen Locations. Wandeln Sie diese ebenfalls in ein `NavigationDetail` um und melden Sie dem `NavigationListener` die neuen Navigationsdaten. Teilen Sie ihm dann außerdem mit, dass ein Ortungssignal gefunden wurde.
Mit den beiden Methoden `onProviderDisabled` und `onProviderEnabled` kann der Activity mitgeteilt werden, dass der Location-Dienst des Geräts deaktiviert wurde, um dem Benutzer in der Activity eine Rückmeldung zu geben.

Um die Navigation zu stoppen, können Sie dem `LocationManager` einfach Bescheid geben, dass der LocationListener keine Updates mehr erhalten soll.

### Berechnen der Distanz
Vervollständigen Sie die vorgegebene Methode `getEstimatedDistanceForLocation` mithilfe der Methoden der `Location`-Klasse.

### Starten der Navigation
Verwenden Sie Ihren `NavigationController` nun in der `NavigationActivity`. Registrieren Sie diese dafür zunächst als Listener.
Anschließend können Sie die `Destination` festlegen. Diese erhalten Sie aus dem `JSON`-String, den Sie aus dem Intent auslesen und parsen können. Den notwendigen Schlüssel für das Extra finden Sie in der `AppConfig`-Klasse

Sobald das Ziel festgelegt wurde, können Sie die Navigation über den `NavigationController` starten.
**Hinweis:** Wenn die App minimiert wird, sollten Sie die Navigation im Code manuell stoppen.

## Anhang
### Screenshots

| | | |
|-|-|-|
|![Screenshot der SightSeer-App](./docs/screenshot-1.png "Berechtigung"){ height=8cm } |![Screenshot der SightSeer-App](./docs/screenshot-2.png "LocationList Activity"){ height=8cm } |![Screenshot der SightSeer-App](./docs/screenshot-3.png "Navigation Activity"){ height=8cm } |

