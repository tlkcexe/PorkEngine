package engine;

// Σύστημα για να ελέγχει συνθήκες, όπως αν ένα αντικείμενο μπορεί να ξεκλειδώσει μια πόρτα.
public class ConditionSystem {

    // Ελέγχει αν ένα αντικείμενο μπορεί να χρησιμοποιηθεί για να ξεκλειδώσει μια exit.
    public static boolean canUnlock(String itemName, String requiredItemId) {
        if (itemName == null || requiredItemId == null) {
            return false;
        }
        return itemName.equalsIgnoreCase(requiredItemId);
    }

    // Ελέγχει αν μια exit είναι κλειδωμένη και χρειάζεται συγκεκριμένο item.
    public static boolean isLockedAndRequires(String requiredItemId) {
        return requiredItemId != null && !requiredItemId.isEmpty();
    }
}