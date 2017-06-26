package com.base.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.R;
import com.github.customview.MyButton;
import com.github.androidtools.PhoneUtils;


public class MyDialog extends Dialog {

	public MyDialog(Context context) {
		super(context);
	}
	public MyDialog(Context context, int theme) {
		super(context, theme);
	}
	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;
		private OnDismissListener onDismissListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}
		public Builder setPositiveButton(OnClickListener listener) {
			return setPositiveButton("确认",listener);
		}
		public Builder setNegativeButton(int negativeButtonText,
										 OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setNegativeButton(OnClickListener listener) {
			return setNegativeButton("取消",listener);
		}
		public Builder setDismissListener(OnDismissListener listener) {
			this.onDismissListener = listener;
			return this;
		}
		public MyDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final MyDialog dialog=new MyDialog(context, R.style.Dialog);
			dialog.setCanceledOnTouchOutside(false);
			View layout = inflater.inflate(R.layout.my_dialog, null);
			//增加Dialog是否设置标题
			/*if(isNotitle){
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				(layout.findViewById(R.id.title)).setVisibility(View.GONE);
			}else{*/
				// set the dialog title
				((TextView) layout.findViewById(R.id.title)).setText(title==null?"提示":title);
//			}

			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			Window dialogWindow =dialog.getWindow();
			int width = wm.getDefaultDisplay().getWidth();
//			int height = wm.getDefaultDisplay().getHeight();
			// set the confirm button
			if(onDismissListener!=null){
				dialog.setOnDismissListener(onDismissListener);
			}
			if (positiveButtonText != null) {
				((MyButton) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					layout.findViewById(R.id.positiveButton)
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
				layout.findViewById(R.id.v_xian).setVisibility(View.GONE);
				((MyButton) layout.findViewById(R.id.negativeButton)).setBottomRightRadius(PhoneUtils.dip2px(context, 8));
				((MyButton) layout.findViewById(R.id.negativeButton)).complete();
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((MyButton) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					layout.findViewById(R.id.negativeButton)
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
				layout.findViewById(R.id.v_xian).setVisibility(View.GONE);
				((MyButton) layout.findViewById(R.id.positiveButton)).setBottomLeftRadius(PhoneUtils.dip2px(context, 8));
				((MyButton) layout.findViewById(R.id.positiveButton)).complete();
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {

				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.ll_dialog)).removeViews(0,2);
				((LinearLayout) layout.findViewById(R.id.ll_dialog)).addView(
						contentView, 0, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));

			}
			dialog.setContentView(layout);
			WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//			p.height = (int) (width* 0.7); // 高度设置为屏幕的0.6
			p.width = (int) (width * 0.7); // 宽度设置为屏幕的0.65
			dialogWindow.setAttributes(p);
			return dialog;
		}

	}
	
}
