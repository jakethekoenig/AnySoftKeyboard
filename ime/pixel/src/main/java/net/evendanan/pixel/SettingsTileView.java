package net.evendanan.pixel;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;

/** A custom view from a */
public class SettingsTileView extends LinearLayout {
  private TextView mLabel;
  private ImageView mImage;
  private Drawable mSettingsTile;
  private CharSequence mSettingsLabel;
  private AttributeSet mInitAttrs;
  private boolean mInflated;

  public SettingsTileView(Context context) {
    super(context);
    // Attributes not provided; leave fields null for now.
  }

  public SettingsTileView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mInitAttrs = attrs;
  }

  public SettingsTileView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mInitAttrs = attrs;
  }

  private void readAttributes(AttributeSet attrs) {
    final TypedArray array =
        getContext().obtainStyledAttributes(attrs, R.styleable.SettingsTileView);
    try {
      mSettingsTile = array.getDrawable(R.styleable.SettingsTileView_tileImage);
      mSettingsLabel = array.getText(R.styleable.SettingsTileView_tileLabel);
    } finally {
      array.recycle();
    }
  }

  private void setupBasicLayoutConfiguration() {
    setBackgroundResource(R.drawable.transparent_click_feedback_background);
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      setOrientation(LinearLayout.VERTICAL);
      setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
    } else {
      setOrientation(LinearLayout.HORIZONTAL);
      setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ensureInflated();
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ensureInflated();
  }

  private void ensureInflated() {
    if (mInflated) return;
    mInflated = true;

    // Inflate child layout after construction to avoid 'this' escaping constructors
    inflate(getContext(), R.layout.settings_tile_view, this);

    // Read any XML attributes that were passed to the constructor
    readAttributes(mInitAttrs);
    mInitAttrs = null;

    mImage = findViewById(R.id.tile_image);
    if (mSettingsTile != null) {
      mImage.setImageDrawable(mSettingsTile);
    }
    mLabel = findViewById(R.id.tile_label);
    if (mSettingsLabel != null) {
      mLabel.setText(mSettingsLabel);
    }

    setupBasicLayoutConfiguration();
  }

  public CharSequence getLabel() {
    return mLabel.getText();
  }

  public void setLabel(CharSequence label) {
    mLabel.setText(label);
  }

  public Drawable getImage() {
    return mImage.getDrawable();
  }

  public void setImage(@DrawableRes int imageId) {
    mImage.setImageResource(imageId);
  }
}
