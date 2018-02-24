class Property{
		int label;
		int numbpixels;
		//boundingBox:
		int minRow;
		int minCol;
		int maxRow;
		int maxCol;
		Property(int label){
			this.label=label;
			minRow=999;
			minCol=999;
			maxRow=0;
			maxCol=0;
		}
	}