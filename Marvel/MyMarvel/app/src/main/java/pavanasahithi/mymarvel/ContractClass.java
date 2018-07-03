package pavanasahithi.mymarvel;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContractClass {
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "pavanasahithi.mymarvel.udacity";
    public static final Uri BASE_CONTENT = Uri.parse(SCHEME + AUTHORITY);
    public static final class TableEntry implements BaseColumns {
        public static final String TableName = "Comics";
        public static final Uri CONTENT_URI =
                BASE_CONTENT.buildUpon().appendPath(TableName).build();
        public static final String DatabaseName = "MyComicDataBase";
        public static final String ColumnId = "ID";
        public static final String ColumnName = "NAME";
        public static final String ColumnURL = "URL";
        public static final String ColumnIntelligence = "INTELLIGENCE";
        public static final String ColumnSTRENGTH = "STRENGTH";
        public static final String ColumnSPEED = "SPEED";
        public static final String ColumnDURABILITY = "DURABILITY";
        public static final String ColumnPOWER = "POWER";
        public static final String ColumnCOMBAT = "COMBAT";
        public static final String ColumnFullName = "FULLNAME";
        public static final String ColumnAlterEGOS = "ALTEREGOS";
        public static final String ColumnBIRTH = "BIRTH";
        public static final String ColumnFirstApp = "FIRSTAPPEARANCE";
        public static final String ColumnPublisher = "PUBLISHER";
        public static final String ColumnAlignment = "ALIGNMENT";
        public static final String ColumnGender = "GENDER";
        public static final String ColumnRace = "RACE";
        public static final String ColumnEye = "EyeColor";
        public static final String ColumnHair = "HairColor";
        public static final String ColumnOccupation= "Occupation";
        public static final String ColumnAff = "GroupAff";
        public static final String ColumnAliases="ALIASES";
        public static final String ColumnHeight="HEIGHT";
        public static final String ColumnWeight="WEIGHT";
        public static final String ColumnRelatives = "RELATIVES";
    }
}
