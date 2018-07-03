package pavanasahithi.mymarvel;

import android.os.Parcel;
import android.os.Parcelable;

public class AllCharactersPojo implements Parcelable{
    String id;
    String name;
    String intelligence;
    String strength;
    String speed;
    String durability;
    String power;
    String combat;
    String full_name;
    String alter_egos;
    String alias;
    String place_of_birth;
    String first_appearance;
    String publisher;
    String alignment;
    String gender;
    String race;
    String height;
    String weight;
    String eye_color;
    String hair_color;
    String occupation;
    String base;
    String group_affiliation;
    String relatives;
    String url;
    public AllCharactersPojo(String id, String name, String intelligence, String strength, String speed, String durability,
                             String power, String combat, String full_name, String alter_egos, String aliases, String place_of_birth, String first_appearance,
                             String publisher, String alignment, String gender, String race, String height, String weight, String eye_color, String hair_color,
                             String occupation, String base, String group_affiliation, String relatives, String url) {
        this.id = id;
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.speed = speed;
        this.durability = durability;
        this.power = power;
        this.combat = combat;
        this.full_name = full_name;
        this.alter_egos = alter_egos;
        this.alias = aliases;
        this.place_of_birth = place_of_birth;
        this.first_appearance = first_appearance;
        this.publisher = publisher;
        this.alignment = alignment;
        this.gender = gender;
        this.race = race;
        this.height = height;
        this.weight = weight;
        this.eye_color = eye_color;
        this.hair_color = hair_color;
        this.occupation = occupation;
        this.base = base;
        this.group_affiliation = group_affiliation;
        this.relatives = relatives;
        this.url = url;
    }

    protected AllCharactersPojo(Parcel in) {
        id = in.readString();
        name = in.readString();
        intelligence = in.readString();
        strength = in.readString();
        speed = in.readString();
        durability = in.readString();
        power = in.readString();
        combat = in.readString();
        full_name = in.readString();
        alter_egos = in.readString();
        alias = in.readString();
        place_of_birth = in.readString();
        first_appearance = in.readString();
        publisher = in.readString();
        alignment = in.readString();
        gender = in.readString();
        race = in.readString();
        height = in.readString();
        weight = in.readString();
        eye_color = in.readString();
        hair_color = in.readString();
        occupation = in.readString();
        base = in.readString();
        group_affiliation = in.readString();
        relatives = in.readString();
        url = in.readString();
    }

    public static final Creator<AllCharactersPojo> CREATOR = new Creator<AllCharactersPojo>() {
        @Override
        public AllCharactersPojo createFromParcel(Parcel in) {
            return new AllCharactersPojo(in);
        }

        @Override
        public AllCharactersPojo[] newArray(int size) {
            return new AllCharactersPojo[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public String getStrength() {
        return strength;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDurability() {
        return durability;
    }

    public String getPower() {
        return power;
    }

    public String getCombat() {
        return combat;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getAlter_egos() {
        return alter_egos;
    }

    public String getAliases() {
        return alias;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getFirst_appearance() {
        return first_appearance;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAlignment() {
        return alignment;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getEye_color() {
        return eye_color;
    }

    public String getHair_color() {
        return hair_color;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getBase() {
        return base;
    }

    public String getGroup_affiliation() {
        return group_affiliation;
    }

    public String getRelatives() {
        return relatives;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(intelligence);
        dest.writeString(strength);
        dest.writeString(speed);
        dest.writeString(durability);
        dest.writeString(power);
        dest.writeString(combat);
        dest.writeString(full_name);
        dest.writeString(alter_egos);
        dest.writeString(alias);
        dest.writeString(place_of_birth);
        dest.writeString(first_appearance);
        dest.writeString(publisher);
        dest.writeString(alignment);
        dest.writeString(gender);
        dest.writeString(race);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(eye_color);
        dest.writeString(hair_color);
        dest.writeString(occupation);
        dest.writeString(base);
        dest.writeString(group_affiliation);
        dest.writeString(relatives);
        dest.writeString(url);
    }
}
