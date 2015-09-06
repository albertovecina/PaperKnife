#Paper Knife
**Decoupler tool for Android Adapters**

##How to use

1. Make your model object to implements CellElement interface

		public class Item implements CellElement {
		}

2. Annotate your methods to mark them as a data source, providing an id.

		@DataSource("Title")
    	public String getTitle() {
      		return title;
    	}
 
3. Implements a class to handle the row views (a view holder) and implements the CellTarget interface

		private static class ViewHolder implements CellTarget {
		} 
		
4. [OPTIONAL] Implements a cell provider. The cell provider is an aditional information source. The cell provider sources receives an instance of your model as a paramenter. Implementes the CellProvider interface and annotate your source methods.

		public class SamplePresenterImpl implements SamplePresenter, CellProvider {
			...
			
		    @DataSource("Check")
		    public boolean isOnFavouritesList(Item item) {
		        return mInteractor.getFavouritesList().contains(item);
		    }
	
		  	...
		}
    	
5. Implements methods in your cell target to manage the views, and mark them as data targets with the annotation DataTarget, sharing your data source id

		@DataTarget("Title")
		public void setTitle(String title) {
           mTextViewTitle.setText(title);
        }
        
6. Execute the data mapping in your getView method

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {

        	....

        	PaperKnife.map(mList.get(position))
                .with(mCellProvider)
                .into(viewHolder);
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
        
        

			