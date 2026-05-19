Σχέδιο Ανάπτυξης Λογισμικού (Project Plan) & Αρχιτεκτονική ΑνάλυσηΜάθημα: Μεθοδολογία ΠρογραμματισμούΟμάδα Ανάπτυξης:Χατζηβασιλείου Αριστοτέλης (@tlkcexe)Βασιλειάδης Σωτήριος (@Digbasanis)Κοζάρης Αλέξανδρος (@alkozaris)Οδηγίες & Συνοδευτική Τεκμηρίωση (Project Documentation)Στον κεντρικό κατάλογο του αποθετηρίου (repository) έχουν προστεθεί όλα τα απαραίτητα αρχεία τεκμηρίωσης και οδηγοί για την τελική παρουσίαση:Οδηγός Χρήστη & Δημιουργού: Ολοκληρωμένος Οδηγός Χρήστη (διαθέσιμες εντολές) και Οδηγός Δημιουργού (περιγραφή δομής JSON για τη σύνταξη νέων σεναρίων).Αναλυτικό Σκονάκι Παρουσίασης: Αναλυτικό «Σκονάκι» Παρουσίασης με πλήρη επεξήγηση της αρχιτεκτονικής, των σχεδιαστικών προτύπων (Command Pattern, κ.λπ.) και της λειτουργίας κάθε αρχείου κώδικα (ΤΙ κάνει και ΠΩΣ).Λύση για το The Gallery Mystery: Αναλυτικός βήμα-βήμα οδηγός (λύση) για την πλήρη ολοκλήρωση του παιχνιδιού The Gallery Mystery (demo_game.json).Λύση για το Cold Horizon Station: Αναλυτικός βήμα-βήμα οδηγός (λύση) για την πλήρη ολοκλήρωση του παιχνιδιού Cold Horizon Station (demo_game2.json).Οδηγίες Εκτέλεσης της ΕφαρμογήςΗ μηχανή PorkEngine βασίζεται στη Java και χρησιμοποιεί την εξωτερική βιβλιοθήκη Gson για το parsing των αρχείων JSON.Επιλογή 1: Εκτέλεση μέσω IDE (VS Code, IntelliJ, Eclipse)Ανοίξτε τον φάκελο PorkEngine στο περιβάλλον ανάπτυξης (IDE) που χρησιμοποιείτε.Εάν η βιβλιοθήκη Gson δεν αναγνωρίζεται αυτόματα, κάντε δεξί κλικ στο αρχείο lib/gson-2.10.1.jar και επιλέξτε Add as Library ή Add to Build Path.Ανοίξτε το αρχείο src/Main.java, κάντε δεξί κλικ και επιλέξτε Run.Επιλογή 2: Εκτέλεση μέσω Τερματικού (Κονσόλας)Ανοίξτε το τερματικό σας, μεταβείτε στον φάκελο PorkEngine και εκτελέστε την αντίστοιχη εντολή:Για Windows (CMD / PowerShell):java -cp "bin;lib\gson-2.10.1.jar" Main
Για Mac / Linux:java -cp "bin:lib/gson-2.10.1.jar" Main
Αρχιτεκτονική Συστήματος (UML)classDiagram
    %% ==========================================
    %% 1. MODEL LAYER
    %% ==========================================
    class Player {
        +currentLocation : Room
        +inventory : List~Item~
    }
    class Room {
        +description : String
        +exits : List~Exit~
        +items : List~Item~
        +npcs : List~NPC~
    }
    class Exit {
        +targetRoom : Room
        +isLocked : boolean
    }
    class Item {
        +name : String
        +description : String
    }
    class NPC {
        +name : String
        +stateMachine : NpcStateMachine
    }

    Player "1" --> "1" Room : currentLocation
    Player "1" *-- "*" Item : has inventory
    Room "1" *-- "*" Exit : has exits
    Room "1" *-- "*" Item : has items
    Room "1" *-- "*" NPC : has NPCs
    Exit "*" --> "1" Room : leads to

    %% ==========================================
    %% 2. ENGINE CORE
    %% ==========================================
    class GameEngine {
        +mainLoop()
    }
    class GameState {
        +player : Player
        +currentSessionData
    }
    class ConsoleUI {
        +print(message)
        +getInput() : String
    }

    GameEngine --> GameState
    GameEngine --> ConsoleUI
    GameState --> Player

    %% ==========================================
    %% 3. GAME LOADER SYSTEM
    %% ==========================================
    class GameLoader {
        <<interface>>
        +loadGame()
    }
    class JsonGameLoader {
        +parseJson()
    }
    class RoomRegistry {
        +getRoom(id)
    }
    class ItemRegistry {
        +getItem(id)
    }

    GameLoader <|.. JsonGameLoader
    JsonGameLoader ..> RoomRegistry : populates
    JsonGameLoader ..> ItemRegistry : populates

    %% ==========================================
    %% 4. COMMAND SYSTEM
    %% ==========================================
    class CommandDispatcher {
        +dispatch(input)
    }
    class CommandParser {
        +tokenize()
    }
    class SynonymMap {
        +getPrimaryVerb()
    }
    class CommandRegistry {
        +getCommand(verb)
    }
    class Command {
        <<interface>>
        +execute(args, gameState)
    }
    
    %% Διακριτές Εντολές βάσει χρονοδιαγράμματος
    class GoCommand
    class TakeCommand
    class DropCommand
    class LookCommand
    class MultiObjectCommand
    class UseCommand
    class InspectCommand
    class CombineCommand
    class TalkCommand
    class GiveCommand

    CommandDispatcher --> CommandParser
    CommandDispatcher --> CommandRegistry
    CommandParser --> SynonymMap
    CommandRegistry *-- "*" Command : maps verbs to

    Command <|.. GoCommand
    Command <|.. TakeCommand
    Command <|.. DropCommand
    Command <|.. LookCommand
    Command <|.. MultiObjectCommand
    Command <|.. UseCommand
    Command <|.. InspectCommand
    Command <|.. CombineCommand
    Command <|.. TalkCommand
    Command <|.. GiveCommand

    GameEngine --> CommandDispatcher

    %% ==========================================
    %% 5. EVENT & CONDITION SYSTEM
    %% ==========================================
    class ConditionSystem {
        +checkRequirements()
    }
    class EventSystem {
        +triggerEvent()
    }
    class GameFlags {
        +winCondition : boolean
        +loseCondition : boolean
    }

    ConditionSystem ..> GameState : reads
    EventSystem --> GameFlags : updates
    Command ..> ConditionSystem : checks
    Command ..> EventSystem : triggers

    %% ==========================================
    %% 6. NPC SYSTEM (BONUS)
    %% ==========================================
    class NpcStateMachine {
        +currentState
        +transition()
    }
    class NpcRegistry {
        +getNpc(id)
    }
    class NpcStagingManager {
        +updateNpcLocations()
    }

    NPC "1" *-- "1" NpcStateMachine
    NpcRegistry *-- "*" NPC
    NpcStagingManager ..> NpcRegistry
    JsonGameLoader ..> NpcRegistry : populates

1. Ποιες είναι οι απαιτήσεις του projectΑντικείμενο του έργου είναι η αντικειμενοστραφής σχεδίαση (ΟΟΡ) και υλοποίηση μιας επαναχρησιμοποιήσιμης μηχανής (engine) για text-based adventure παιχνίδια σε Java. Ο πρωταρχικός στόχος είναι η εφαρμογή θεμελιωδών αρχών αρχιτεκτονικής λογισμικού, με έμφαση στον διαχωρισμό αρμοδιοτήτων (Separation of Concerns) και στο Open/Closed Principle (OCP).Βάσει των προδιαγραφών, δεν αναπτύσσεται ένα μεμονωμένο παιχνίδι, αλλά μια αυστηρά data-driven αρχιτεκτονική. Ο "εγκέφαλος" του συστήματος (Engine) πρέπει να διαχωρίζεται πλήρως από το περιεχόμενο (Game Content). Η μηχανή οφείλει να είναι "ανοιχτή" σε επεκτάσεις (π.χ. νέα αντικείμενα, νέες εντολές, νέοι κανόνες) μέσω της δημιουργίας νέων κλάσεων, αλλά "κλειστή" σε τροποποιήσεις του υπάρχοντος πηγαίου κώδικα.Η αρχιτεκτονική αυτή εγγυάται την επαναχρησιμοποίηση της μηχανής για πολλαπλά, διαφορετικά Game Instances, καθώς και την άμεση προσαρμοστικότητά της στο απρόβλεπτο Change Scenario (εβδομάδες 7-8). Για την επίτευξη της μέγιστης βαθμολογίας (12/10), η μηχανή υποστηρίζει και την ένταξη αυτόνομων χαρακτήρων (NPCs).2. Λίστα ΑπαιτήσεωνΛειτουργικές Απαιτήσεις:ΛΑ-1 (Game Content): Δυναμική φόρτωση του κόσμου (Rooms, Exits, Items, NPCs) αποκλειστικά από εξωτερικά αρχεία μορφής JSON, χωρίς τη χρήση builder/configuration κλάσεων με hardcoded δεδομένα εντός της Java.ΛΑ-2 (Χώροι & Πλοήγηση): Κάθε χώρος (Room) διαθέτει περιγραφή, λίστα αντικειμένων/NPCs και συνδέσεις με άλλους χώρους για την πλοήγηση του παίκτη.ΛΑ-3 (Παίκτης & Αντικείμενα): Ο παίκτης (Player) διαθέτει τρέχουσα τοποθεσία, κατάσταση και inventory. Μπορεί να συλλέγει (take), να αφήνει (drop), να χρησιμοποιεί (use) και να εξετάζει (inspect) αντικείμενα.ΛΑ-4 (Σύστημα Εντολών): Επεκτάσιμο, data-oriented Command System που υποστηρίζει συνώνυμα ρημάτων (π.χ. take/grab/pick up) και πολύπλοκες προθετικές φράσεις με πολλαπλά αντικείμενα (π.χ. "give toolkit to engineer").ΛΑ-5 (Game State): Διαρκής παρακολούθηση της κατάστασης της τρέχουσας σεσιόν (τρέχον δωμάτιο, inventory, win/lose rules).ΛΑ-6 (Bonus 12/10 - NPCs): Υποστήριξη Non-Player Characters με δική τους κατάσταση (state machine) η οποία μεταβάλλεται βάσει των πράξεων του παίκτη (π.χ. dialog, staging, item exchange).Μη Λειτουργικές / Αρχιτεκτονικές Απαιτήσεις:ΜΑ-1: Πλήρης απουσία hardcoded συγκρίσεων εντολών (απαγορεύονται τα if/switch με literal strings τύπου command.equals("go north")).ΜΑ-2: Υποστήριξη εκτέλεσης ενός εντελώς διαφορετικού game instance (DemoGame2) στον ίδιο engine, χωρίς καμία απολύτως αλλαγή στον κώδικα.ΜΑ-3: Ύπαρξη Extension Points για την ομαλή ενσωμάτωση του Change Scenario (π.χ. νέος κανόνας time limit ή σύστημα υγείας).3. Λειτουργικές ΜονάδεςΤο σύστημα οργανώνεται στα εξής αυτόνομα υποσυστήματα:Model Layer: Αφηρημένες αναπαραστάσεις των οντοτήτων. Περιέχει τα interfaces και τις abstract κλάσεις (Room, Exit, Item, Player).Engine Core: Περιλαμβάνει την κλάση GameEngine (κεντρικό game loop), το GameState (συντονιστής προόδου) και το ConsoleUI για την απομόνωση του Input/Output.Game Loader System: Υπεύθυνο για την εξωτερική φόρτωση δεδομένων. Περιλαμβάνει τον JsonGameLoader και τις δομές in-memory αποθήκευσης (RoomRegistry, ItemRegistry).Command System: Το σύστημα επεξεργασίας κειμένου. Απαρτίζεται από τον CommandParser (tokenizer & synonym resolver), το data-driven CommandRegistry (verb-to-command mapping), τον CommandDispatcher και τις διακριτές υλοποιήσεις εντολών (π.χ. GoCommand, TakeCommand, MultiObjectCommand).Event & Condition System: Διαχειρίζεται τους περιορισμούς του περιβάλλοντος (π.χ. locked states) και τα GameFlags για τον τερματισμό του παιχνιδιού.NPC System (Bonus): Υποσύστημα που ενσωματώνει το NpcStateMachine (states/transitions), το NpcRegistry και τον NpcStagingManager.4. Περιπτώσεις ΧρήσηςΠΧ-01 (Φόρτωση): Ο engine φορτώνει το αρχείο JSON. Δημιουργεί δυναμικά τα Rooms και τα συνδέει μέσω Exits χωρίς σφάλματα.ΠΧ-02 (Απλή Κίνηση & Περιγραφή): Ο παίκτης εισάγει go north. Το σύστημα ελέγχει τα exits του τρέχοντος δωματίου. Αν το exit είναι ελεύθερο, το GameState ανανεώνει την τοποθεσία και τυπώνει την περιγραφή του νέου χώρου. Αν όχι, τυπώνει "The door is locked." (ή φιλικό μήνυμα λάθους σε αδύνατη κίνηση).ΠΧ-03 (Αλληλεπίδραση Inventory): Ο παίκτης εισάγει take key. O engine αφαιρεί το αντικείμενο από το Room και το προσθέτει στο Player Inventory.ΠΧ-04 (Σύνθετη Εντολή - Prepositions & Smart Use): Ο παίκτης εισάγει use badge. Το UseCommand εκτελεί έξυπνο έλεγχο: σκανάρει αυτόματα όλες τις εξόδους του δωματίου, βρίσκει την αντίστοιχη κλειδωμένη πόρτα και την ξεκλειδώνει χωρίς να απαιτείται καθορισμός της κατεύθυνσης από τον χρήστη.ΠΧ-05 (Change Scenario - OCP Verification): Ο διδάσκων ζητά νέα λειτουργικότητα (π.χ. combine items). Η ομάδα προσθέτει μόνο μια νέα κλάση CombineCommand και ένα νέο entry στο JSON, χωρίς να αγγίξει τον CommandParser ή τον GameEngine.ΠΧ-06 (Bonus - NPC Interaction): Ο παίκτης συναντά έναν φρουρό και γράφει give catalog to curator. Το NpcStateMachine του χαρακτήρα μεταβαίνει στην κατάσταση "happy", ενεργοποιώντας νέες γραμμές διαλόγου και ξεκλειδώνοντας τις κλειδωμένες διόδους του χώρου.5. Project Planning & Κατανομή Εργασιών+ v0.1: Αρχιτεκτονική & Σκελετός (Εβδομάδες 1-2)
+ • Χατζηβασιλείου Α.: Υλοποίηση του πυρήνα του Engine (GameEngine, GameState, core loop).
+ • Κοζάρης Α.: Σχεδιασμός Model Layer (Interfaces για Room, Exit, Item, Player) και υλοποίηση του ConsoleUI.
+ • Βασιλειάδης Σ.: Δημιουργία σκελετού CommandDispatcher, ορισμός GameLoader Interface και αρχικό stub loader για το πρώτο testing. Συμφωνία ονοματολογίας συνόλου.

+ v0.2: Λειτουργικό Engine Core (Εβδομάδες 3-4)
+ • Βασιλειάδης Σ.: Υλοποίηση μηχανισμού Inventory (Player state) και λογική ενημέρωσης τρέχοντος δωματίου.
+ • Κοζάρης Α.: Πλήρης ανάπτυξη του JsonGameLoader (parsing δομής JSON) και δημιουργία του RoomRegistry.
+ • Χατζηβασιλείου Α.: Υλοποίηση βασικών εντολών (GoCommand, TakeCommand, DropCommand, LookCommand) και συγγραφή του 1ου JSON game content (τουλάχιστον 5 δωμάτια).

+ v0.3: Command System (Εβδομάδες 5-6)
+ • Χατζηβασιλείου Α.: Προγραμματισμός CommandParser (tokenizer) και SynonymMap (εξωτερική data-driven φόρτωση συνωνύμων).
+ • Βασιλειάδης Σ.: Υλοποίηση CommandRegistry (δυναμικό mapping ρημάτων) και λογική για MultiObjectCommand (targets & prepositions).
+ • Κοζάρης Α.: Ανάπτυξη σύνθετων εντολών (Smart UseCommand, InspectCommand) και του ConditionSystem (για locked doors / item requirements).

+ v1.0: Polish, Change Scenario & NPCs [Bonus] (Εβδομάδες 7-10)
+ • Χατζηβασιλείου Α.: Υλοποίηση του EventSystem / GameFlag (win/lose conditions) και προετοιμασία των Extension Points του κώδικα για την ενσωμάτωση του Change Scenario.
+ • Κοζάρης Α.: Ανάπτυξη του NPC υποσυστήματος: NpcStateMachine (καταστάσεις, transitions) και NpcRegistry.
+ • Βασιλειάδης Σ.: Υλοποίηση εντολής TalkCommand / GiveCommand για NPCS, και συγγραφή του DemoGame2.json (Δεύτερο παιχνίδι σε διαφορετικό setting) για την απόδειξη επαναχρησιμοποίησης του Engine στην τελική παρουσίαση.
