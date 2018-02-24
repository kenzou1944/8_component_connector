import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Main {

	static void print(Eight_CC CC, BufferedWriter Write){
		//print zeroFramedAry
		try{
			for(int i=1;i<CC.zeroFramedAry.length;i++){
				for(int j=1;j<CC.zeroFramedAry[i].length;j++){
					if(CC.zeroFramedAry[i][j]>0){
						Write.write(CC.zeroFramedAry[i][j]+ " ");
					}
					else{
						Write.write("  ");
					}
				}
				Write.newLine();
			}	
			Write.write("Print EQAry");
			Write.newLine();
			for(int i=0;i<CC.newLabel+1;i++){
				Write.write(CC.EQAry[i]+" ");
			}
			Write.newLine();
			Write.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void pass1(Eight_CC CC){
		CC.newLabel=0;
		System.out.println();
		for(int i=1;i<CC.zeroFramedAry.length-1;i++){
			for(int j=1;j<CC.zeroFramedAry[i].length-1;j++){
				if(CC.zeroFramedAry[i][j]>0){
					CC.load_Into_neighbor_pass1(i,j);
					CC.check_pass1(i,j);
				}
			}
		}
	}
	static void pass2(Eight_CC CC){
		for(int i=CC.zeroFramedAry.length-1;i>1;i--){
			for(int j=CC.zeroFramedAry[i].length-1;j>1;j--){
				if(CC.zeroFramedAry[i][j]>0){
					CC.load_Into_neighbor_pass2(i,j);
					CC.check_pass2(i,j);
				}
			}
		}
	}
	static void pass3(Eight_CC CC){
		CC.Manage_Eq_Table();
		for(int i=1;i<CC.zeroFramedAry.length-1;i++){
			for(int j=1;j<CC.zeroFramedAry[i].length-1;j++){
				if(CC.zeroFramedAry[i][j]>0){
					CC.zeroFramedAry[i][j]=CC.EQAry[CC.zeroFramedAry[i][j]];
				}
			}
		}
	}
	public static void main(String args[]) {
		File file=new File(args[0]);
		Scanner sc;

		try {
			File ReadArg1=new File(args[0].substring(0,args[0].length()-4)+"_Result_of_Three_Pass");
			BufferedWriter WriteArg1 = new BufferedWriter(new FileWriter(ReadArg1));
			
			File ReadArg2=new File(args[0].substring(0,args[0].length()-4)+"_Result_of_pass-3");
			BufferedWriter WriteArg2 = new BufferedWriter(new FileWriter(ReadArg2));
			
			File ReadArg3=new File(args[0].substring(0,args[0].length()-4)+"_Result_of_Components");
			BufferedWriter WriteArg3 = new BufferedWriter(new FileWriter(ReadArg3));	

			sc = new Scanner(file);
			int numRows=sc.nextInt();
			int numCols=sc.nextInt();
			int minVal=sc.nextInt();
			int maxVal=sc.nextInt();
			int newMin=100;
			int newMax=0;
			Eight_CC CC=new Eight_CC(numRows,numCols);
			while(sc.hasNext()){
				int myInt=sc.nextInt();
				CC.load_image(myInt);
			}
			WriteArg1.write(" Pretty print Pass-1");
			WriteArg1.newLine();
			pass1(CC);
			print(CC,WriteArg1);
			WriteArg1.write(" Pretty print Pass-2");
			WriteArg1.newLine();
			pass2(CC);
			print(CC,WriteArg1);
			WriteArg1.write(" Pretty print Pass-3");
			WriteArg1.newLine();
			pass3(CC);
			print(CC,WriteArg1);
			
			WriteArg2.write(" Pretty print Pass-3");
			WriteArg2.newLine();
			print(CC,WriteArg2);
			
			//calculate min and max
			for(int i=1;i<CC.zeroFramedAry.length-1;i++){
				for(int j=1;j<CC.zeroFramedAry[i].length-1;j++)
				{
					if(CC.zeroFramedAry[i][j]>0){
						if(CC.zeroFramedAry[i][j]>newMax){
							newMax=CC.zeroFramedAry[i][j];
						}
						else if(CC.zeroFramedAry[i][j]<newMin){
							newMin=CC.zeroFramedAry[i][j];	
						}
					}
				}
			}
			WriteArg3.write(numRows+" "+numCols+" "+ newMin+" "+newMax);
			WriteArg3.newLine();
			int[] counter=new int[newMax+1];
			Property[] property=new Property[newMax+1]; // component size +1
			for(int i=1;i<newMax+1;i++){
				Property list=new Property(i);
				property[i]=list; //label 1-12
			}
			for(int i=1;i<CC.zeroFramedAry.length-1;i++){
				for(int j=1;j<CC.zeroFramedAry[i].length-1;j++)
				{
					if(CC.zeroFramedAry[i][j]>0){ // non-zero
						counter[CC.zeroFramedAry[i][j]]++;

						// calculate minrow,mincol,maxrow,maxcol
						if(property[CC.zeroFramedAry[i][j]].minRow>i){
							property[CC.zeroFramedAry[i][j]].minRow=i;	
						}
						if(property[CC.zeroFramedAry[i][j]].minCol>j){
							property[CC.zeroFramedAry[i][j]].minCol=j;
						}
						if(property[CC.zeroFramedAry[i][j]].maxRow<i){
							property[CC.zeroFramedAry[i][j]].maxRow=i;
						}
						if(property[CC.zeroFramedAry[i][j]].maxCol<j){
							property[CC.zeroFramedAry[i][j]].maxCol=j;
						}				
					}
				}
			}		
			// find max i and j, using min i and j
			for(int i=1;i<newMax+1;i++){
				property[i].numbpixels=counter[i];
				WriteArg3.write(property[i].label+" ");
				WriteArg3.newLine();
				WriteArg3.write(property[i].numbpixels+" ");
				WriteArg3.newLine();
				WriteArg3.write(property[i].minRow+" "+property[i].minCol);
				WriteArg3.newLine();
				WriteArg3.write(property[i].maxRow+" "+property[i].maxCol);
				WriteArg3.newLine();
			}
			//close files
			sc.close();
			WriteArg1.close();
			WriteArg2.close();
			WriteArg3.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}