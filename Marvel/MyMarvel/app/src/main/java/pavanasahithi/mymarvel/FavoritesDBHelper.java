package pavanasahithi.mymarvel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pavanasahithi.mymarvel.ContractClass.TableEntry;

public class FavoritesDBHelper extends SQLiteOpenHelper {
    public static final int version = 1;
    public static final String Create_query =
            "create table " + TableEntry.TableName + "(" +
                    TableEntry.ColumnId + " text primary key," +
                    TableEntry.ColumnName + " text NOT NULL," +
                    TableEntry.ColumnIntelligence + " text NOT NULL,"+
                    TableEntry.ColumnSTRENGTH + " text NOT NULL," +
                    TableEntry.ColumnSPEED + " text NOT NULL," +
                    TableEntry.ColumnDURABILITY + " text NOT NULL,"+ TableEntry.ColumnPOWER + " text NOT NULL," +
                    TableEntry.ColumnCOMBAT + " text NOT NULL," + TableEntry.ColumnFullName + " text NOT NULL," +
                    TableEntry.ColumnAlterEGOS + " text NOT NULL," +TableEntry.ColumnAliases + " text NOT NULL,"+
                    TableEntry.ColumnBIRTH + " text NOT NULL," +TableEntry.ColumnFirstApp + " text NOT NULL," +
                    TableEntry.ColumnPublisher + " text NOT NULL," + TableEntry.ColumnAlignment + " text NOT NULL," +
                    TableEntry.ColumnGender + " text NOT NULL," + TableEntry.ColumnRace + " text NOT NULL," +
                    TableEntry.ColumnHeight+" text NOT NULL,"+TableEntry.ColumnWeight+" text NOT NULL,"+
                    TableEntry.ColumnEye + " text NOT NULL," + TableEntry.ColumnHair + " text NOT NULL," +
                    TableEntry.ColumnOccupation + " text NOT NULL," + TableEntry.ColumnAff + " text NOT NULL," +
                    TableEntry.ColumnRelatives + " text NOT NULL," + TableEntry.ColumnURL + " text NOT NULL)";

    public Context context;

    public FavoritesDBHelper(Context context) {
        super(context, TableEntry.DatabaseName, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TableEntry.TableName);
        onCreate(sqLiteDatabase);
    }
}
