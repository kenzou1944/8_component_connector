class Eight_CC{
	int[][] zeroFramedAry;
	int[] NeighborAry;
	int[] EQAry;
	int numRows;
	int numCols;
	int newLabel;
	int Rowcounter=1;
	int Colcounter=1;
	int neighborAry_counter=0;
	Eight_CC(int Rows,int Cols){
		numRows=Rows;
		numCols=Cols;
		zeroFramedAry=new int[numRows+2][numCols+2];
		NeighborAry=new int[5];
		EQAry=new int[numRows*numCols/2];
		for(int i=0;i<EQAry.length;i++){
			EQAry[i]=i;
		}
	}

	void load_Into_neighbor_pass1(int row,int col){
		NeighborAry[NeighborAry.length-1]=zeroFramedAry[row][col];
		row=row-1;
		col=col-1;
		for(int j=0;j<3;j++){
			NeighborAry[neighborAry_counter++]=zeroFramedAry[row][col++];
		}
		NeighborAry[neighborAry_counter++]=zeroFramedAry[row+1][col-3];
		neighborAry_counter=0;
	}

	void load_Into_neighbor_pass2(int row,int col){
		NeighborAry[NeighborAry.length-1]=zeroFramedAry[row][col];
		//i =row, j =col
		NeighborAry[neighborAry_counter++]=zeroFramedAry[row++][col+1];
		col=col-1;
		for(int j=0;j<3;j++){
			NeighborAry[neighborAry_counter++]=zeroFramedAry[row][col++];
		}
		neighborAry_counter=0;
	}

	boolean check_NeighborAry_for_Zero(){
		boolean check=true;
		for(int i=0;i<NeighborAry.length-1;i++){
			if(NeighborAry[i]!=0){
				check=false;
			}
		}
		return check;
	}
	boolean check_NeighborAry_for_All_Identical(){
		int init_value=NeighborAry[0];
		for(int i=1;i<NeighborAry.length;i++){
			if(init_value!=NeighborAry[i]){
				return false;
			}
		}
		return true;
	}
	int check_NeighborAry_for_identical(){
		int counter=0;
		int temp=0;
		for(int i=0;temp==0;i++){
			if(NeighborAry[i]>0){
				temp=NeighborAry[i];
				for(int j=i+1;j<NeighborAry.length-1;j++){
					if(NeighborAry[j]>0 && temp!=NeighborAry[j]){
						return 0;
					}
				}
			}
			else{
				counter++;
			}
			if(counter==NeighborAry.length-1){
				break;
			}
		}
		return temp;
	}
	void check_pass1(int i,int j){
		if(check_NeighborAry_for_Zero()){
			newLabel++;
			zeroFramedAry[i][j]=newLabel;
		}
		else if(!check_NeighborAry_for_Zero()){
			if(check_NeighborAry_for_identical()>0){
				int label=check_NeighborAry_for_identical();
				zeroFramedAry[i][j]=label;
				//case 2 with temp value
			}
			else{
				//case 3
				int min=999;
				for(int index=0;index<NeighborAry.length-1;index++){
					if((NeighborAry[index]>0) && (min>NeighborAry[index])){
						min=NeighborAry[index];
					}
				}
				for(int index=0;index<NeighborAry.length;index++){
					if(NeighborAry[index]>0 && NeighborAry[index]!=min){
						if(NeighborAry[index]!=1){
							EQAry[NeighborAry[index]]=min;
						}
					}
				}
				zeroFramedAry[i][j]=min;
				//case 3 find min and replace with p(i,j)
			}
		}
	}
	void check_pass2(int i,int j){
		if(check_NeighborAry_for_All_Identical()){
			//do nothing for case 1
		}
		else if (check_NeighborAry_for_identical()>0){
			if(zeroFramedAry[i][j]==check_NeighborAry_for_identical()){
				// do nothing for case 2
			}
			else{
				EQAry[zeroFramedAry[i][j]]=check_NeighborAry_for_identical();
				zeroFramedAry[i][j]=check_NeighborAry_for_identical();
			}
		}
		else{ //case 3
			int min=999;
			//calculate min
			for(int index=0;index<NeighborAry.length;index++){
				if((NeighborAry[index]>0) && (min>NeighborAry[index])){
					min=NeighborAry[index];
				}
			}
			// manage EQAry
			updateEQAry(min);
			zeroFramedAry[i][j]=min; // later
		}
	}
	void updateEQAry(int min){
		for(int index=0;index<NeighborAry.length;index++){
			if(NeighborAry[index]>0 && NeighborAry[index]!=min){
				EQAry[NeighborAry[index]]=min;
			}
		}
	}
	void Manage_Eq_Table(){
		int count = 0;
		for (int i=1; i<=newLabel;i++){
			if(EQAry[i]==i){
				EQAry[i]=++count;
			}
			else{
				EQAry[i]=EQAry[EQAry[i]];
			}
		}
	}
	void load_image(int input_value){
		if(Colcounter<zeroFramedAry[Rowcounter].length-2){
			zeroFramedAry[Rowcounter][Colcounter++]=input_value;
		}
		else{
			zeroFramedAry[Rowcounter++][Colcounter]=input_value;
			Colcounter=1;
		}
	}
}