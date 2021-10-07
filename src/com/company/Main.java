package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 900;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";


    public static String[] heroesAttackTypes = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "lucky", "Thor", "Berserk"};
    public static int[] heroesHealth = {290, 280, 250, 300, 700, 450, 380, 290};
    public static int[] heroesDamages = {20, 25, 15, 0, 10, 18, 40, 50};
    public static int roundsNumber = 0;
    public static Random random = new Random();

    public static void chooserDefence() {
        int randomIndex = random.nextInt(heroesAttackTypes.length);
        bossDefenceType = heroesAttackTypes[randomIndex];
        System.out.println("Boss choose: " + bossDefenceType);

    }

    public static void main(String[] args) {
        printStatics();
        while (!isGameFinished()) {
            round();
        }
    }

    private static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }

        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;

    }

    private static void round() {
        roundsNumber++;
        chooserDefence();
        if (!thor()) {
            bossHits();
            treat();
            golem();
            lucky();
            berserk();
        } else {
            System.out.println("Босс оглушен");
        }
        heroesHit();
        printStatics();
    }

    private static boolean thor() {
        return random.nextBoolean();
    }

    private static void berserk() {
        int block = random.nextInt(bossDamage);
        if (heroesHealth[7] > 0) {
            heroesHealth[7] += block;
            bossHealth -= block;
            System.out.println("заблокировал ");
        }
    }

    private static void lucky() {
        boolean l = random.nextBoolean();
        if (l && heroesHealth[5] > 0) {
            heroesHealth[5] += bossDamage;
            System.out.println("Уклонился");
        }
    }

    private static void treat() {
        int n = random.nextInt(100);
        int randomIndex = random.nextInt(heroesHealth.length);

        if (randomIndex == 3) {
            treat();
        } else if (heroesHealth[randomIndex] < 100 && heroesHealth[3] > 0 && heroesHealth[randomIndex] > 0) {
            heroesHealth[randomIndex] += n;
            System.out.println("Medic healed: " + heroesAttackTypes[randomIndex] + " +" + n);
        }
    }

    static void golem() {
        int g = bossDamage / 5;
        for (int i = 0; i < heroesAttackTypes.length; i++) {
            if (i == 4) continue;
            else if (heroesHealth[i] > 0) {
                heroesHealth[i] += g;
                heroesHealth[4] -= g;
            }
        }

    }


    private static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamages.length; i++) {
            if (bossDamage > 0 && heroesHealth[i] > 0) {
                //
                if (bossDefenceType == heroesAttackTypes[i]) {
                    int coef = random.nextInt(9) + 2;
                    if (bossHealth - heroesDamages[i] * coef < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth -= heroesDamages[i] * coef;
                        System.out.println("Critical damages: " + heroesDamages[i] * coef);
                    }
                } else {
                    if (bossHealth - heroesDamages[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamages[i];
                    }
                }
            }
        }


    }

    private static void printStatics() {
        System.out.println("_____ROUND_______" + roundsNumber);
        System.out.println("Boss health:" + bossHealth + "[" + bossDamage + "]\n");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackTypes[i] + " health: " + heroesHealth[i] + " [" + heroesDamages[i] + "]");

        }
        System.out.println("____________________");
    }


}
