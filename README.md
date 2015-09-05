#Paper Knife
**Decoupler tool for Android Adapters**

##How to use

1. Make your model object to implementes CellElement interface

		public class Item implements CellElement {
		}

2. Annotate your methods to mark them as a data source

		@CellSource("Title")
    	public String getTitle() {
      		return title;
    	}
 
3. Implements a class to handle the row views (a view holder) and exteds the ViewTarget interface

		private static class ViewHolder implements ViewTarget {
		} 
    	
4. Implements methods in your view holder to manage the views, and mark them as targets with the annotation CellTarget

		@CellTarget("Title")
		public void setTitle(String title) {
           mTextViewTitle.setText(title);
        }
        
5. Execute the data mapping in your getView method

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {

        	....

        	PaperKnife.map(mList.get(position)).into(viewHolder);
        	return convertView;
    	}
       
##License

	Copyright 2013 Alberto Vecina
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
        
        

			