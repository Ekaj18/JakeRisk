package mapgeneration;

import java.util.ArrayList;

import map.Country;
import map.Map;

public class makeDefaultMap {
	static Country[] countries = new Country[42];
	public static Map makeMap(){
		ArrayList<Integer> bonusList = new ArrayList<>();
		ArrayList<Country> countryList = new ArrayList<>();
		for(int i = 0; i < countries.length; i++){
			if(i < 9){
				countries[i] = new Country(0);
			}
			else if(i < 13){
				countries[i] = new Country(1);
			}
			else if(i < 19){
				countries[i] = new Country(2);
			}
			else if(i < 26){
				countries[i] = new Country(3);
			}
			else if(i < 38){
				countries[i] = new Country(4);
			}
			else{
				countries[i] = new Country(5);
			}
		}
		
		countries[0].setBorders(makeList(new int[] {2,3,38}));
		countries[1].setBorders(makeList(new int[] {1,3,7,9}));
		countries[2].setBorders(makeList(new int[] {1,2,4,7}));
		countries[3].setBorders(makeList(new int[] {3,7,6,5}));
		countries[4].setBorders(makeList(new int[] {4,6,10}));
		countries[5].setBorders(makeList(new int[] {5,4,7,8}));
		countries[6].setBorders(makeList(new int[] {3,4,2,9,8,6}));
		countries[7].setBorders(makeList(new int[] {7,9,6}));
		countries[8].setBorders(makeList(new int[] {2,7,8,22}));
		countries[9].setBorders(makeList(new int[] {5,11,13}));
		countries[10].setBorders(makeList(new int[] {10,12,13}));
		countries[11].setBorders(makeList(new int[] {11,13}));
		countries[12].setBorders(makeList(new int[] {10,11,12,14}));
		countries[13].setBorders(makeList(new int[] {13,15,18,19,20,25}));
		countries[14].setBorders(makeList(new int[] {14,16,18}));
		countries[15].setBorders(makeList(new int[] {15,17,18}));
		countries[16].setBorders(makeList(new int[] {16,18}));
		countries[17].setBorders(makeList(new int[] {14,15,16,17,19,27}));
		countries[18].setBorders(makeList(new int[] {14,18,25,27}));
		countries[19].setBorders(makeList(new int[] {21,14,25,24}));
		countries[20].setBorders(makeList(new int[] {22,23,20,24}));
		countries[21].setBorders(makeList(new int[] {9,21,23}));
		countries[22].setBorders(makeList(new int[] {22,21,24,26}));
		countries[23].setBorders(makeList(new int[] {21,20,25,23,26}));
		countries[24].setBorders(makeList(new int[] {14,19,20,24,26,27}));
		countries[25].setBorders(makeList(new int[] {23,24,25,27,28,29}));
		countries[26].setBorders(makeList(new int[] {19,18,25,26,28,35}));
		countries[27].setBorders(makeList(new int[] {26,27,29,34,35}));
		countries[28].setBorders(makeList(new int[] {26,28,30,34}));
		countries[29].setBorders(makeList(new int[] {29,34,33,32,31}));
		countries[30].setBorders(makeList(new int[] {30,32,38}));
		countries[31].setBorders(makeList(new int[] {30,31,38,33}));
		countries[32].setBorders(makeList(new int[] {30,32,38,37,34}));
		countries[33].setBorders(makeList(new int[] {28,29,30,33,36,35}));
		countries[34].setBorders(makeList(new int[] {27,28,34,36}));
		countries[35].setBorders(makeList(new int[] {34,35,39}));
		countries[36].setBorders(makeList(new int[] {33,38}));
		countries[37].setBorders(makeList(new int[] {31,32,33,37,1}));
		countries[38].setBorders(makeList(new int[] {36,40,41}));
		countries[39].setBorders(makeList(new int[] {39,42}));
		countries[40].setBorders(makeList(new int[] {39,42}));
		countries[41].setBorders(makeList(new int[] {40,41}));
		
		for(Country c: countries){
			countryList.add(c);
		}
		bonusList.add(5);
		bonusList.add(2);
		bonusList.add(3);
		bonusList.add(5);
		bonusList.add(7);
		bonusList.add(2);
		
		Map map  = new Map(countryList, bonusList);
		System.out.println("Default map created.");
		return map;	
	}
	
	private static ArrayList<Country> makeList(int[] nums){
		ArrayList<Country> temp = new ArrayList<>();
		for(int i = 0; i < nums.length; i++){
			temp.add(countries[nums[i]-1]);
		}
		return temp;
	}
}
