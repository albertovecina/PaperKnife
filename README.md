#Paper Knife
**Decoupler tool for Android Adapters**

##How to use

1. Make your model object to implements CellElement interface

		public class Item implements CellElement {
		}
 
2. Implements a class to handle the row views (a view holder) and implements the CellViewHolder interface

		private static class ViewHolder implements CellViewHolder {
		} 

3. Implements methods in your CellViewHolder to manage the views, and mark them as data targets with the annotation DataTarget and providing an id

		@DataTarget("Title")
		public void setTitle(String title) {
           mTextViewTitle.setText(title);
        }
        
		
4. Implements a cell provider. The cell provider the information source to populate de DataTarget methods. Implements the CellDataProvider interface and annotate your source methods sharing the DataTarget id to make the data mapping. The cell provider methods receives an instance of your model as a paramenter.

		public class SamplePresenterImpl implements SamplePresenter, CellDataProvider {
		
			...
		    @DataSource("Title")
		    public String getTitle(Item item) {
		        return item.getTitle();
		    }
		  	...
		  	
		}
    	
5. [OPTIONAL] Implementes a listener provider. The listener provider is a class resposible to create listeners for every view in your row. 
To create a listener provider you need to follow this steps:
	
	1. Implementes the CellListenerProvider interface and mark your provider methods with the ListenerSource Annotation. The ListenerSource annotated methods must receive a CellElement as a parameter.
		
			public class SampleActivity extends Activity implements SampleView, CellListenerProvider {
		
			    @ListenerSource("CheckBox")
			    public OnCheckedChangeListener provideCheckBoxListener(final CellElement cellElement) {
			        return new OnCheckedChangeListener() {
			            @Override
			            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			                mPresenter.onCheckChange(cellElement, isChecked);
			            }
			        };
			    }
			
			    @ListenerSource("Title")
			    public View.OnClickListener provideOnClickListener(final CellElement cellElement) {
			        return new View.OnClickListener() {
			            @Override
			            public void onClick(View v) {
			                mPresenter.onCheckChange(cellElement, true);
			            }
			        };
		    	}
		    	
			}
		
	2. Mark your target views with the annotation ListenerTarget. This views will receive the listener provided by yout CellListenerProvider.
		
			private static class ViewHolder implements CellViewHolder {
		
		        @ListenerTarget("Title")
		        public TextView mTextViewTitle;
		        @ListenerTarget("CheckBox")
		        public CheckBox mCheckBox;
		        ...
		        
		    }


6. Construct or inject a PaperKnife instance and execute the data mapping in your adapter

		public CustomAdapter (Context context, List<? extends CellElement> list,
                          CellDataProvider cellDataProvider, CellListenerProvider cellListenerProvider) {
	        ...
	        mPaperKnife = new PaperKnife(cellDataProvider, cellListenerProvider);
    	}
    	
		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
        	...
			mPaperKnife.bind(mList.get(position), viewHolder);
        	return convertView;
    	}
       
##Download
GRADLE

    compile 'com.vsa:paperknife:1.1'

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
        
        

			
