package fr.arnaudguyon.smartgl.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

// TODO for library
// set default Matrix, and add function set3DMatrix or setCameraPosition/Orientation(2d / 3d pos)
// put all in same package, so that package functions are not accessible from outside, only public

/* package */ abstract class OpenGLView extends GLSurfaceView {

	private OpenGLRenderer	mOpenGLRenderer;

	public OpenGLView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OpenGLView(Context context) {
		super(context);
	}

	@Override
	public void setRenderer(Renderer renderer) throws RuntimeException {

		if (renderer instanceof OpenGLRenderer) {
			mOpenGLRenderer = (OpenGLRenderer) renderer;
			mOpenGLRenderer.setListener(this);
			setEGLContextClientVersion(2);
			ConfigGLSelector selector = new ConfigGLSelector(this);
			if (selector != null) {
				setEGLConfigChooser(selector);
				//setPreserveEGLContextOnPause(true);
				super.setRenderer(mOpenGLRenderer);
			}
		} else {
			throw new RuntimeException("renderer must be a OpenGLRenderer");
		}
	}
	
	public OpenGLRenderer getOpenGLRenderer() {
		return mOpenGLRenderer;
	}

	public void onPreRender(OpenGLRenderer renderer) {
	}

	@Override
	public void onPause() {
		releaseResources();
		if (mOpenGLRenderer != null) {
			mOpenGLRenderer.onPause();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		acquireResources();
		super.onResume();
		if (mOpenGLRenderer != null) {
			mOpenGLRenderer.onResume();
		}
	}

	protected void acquireResources() {}
	protected void releaseResources() {}
	protected void onViewResized(int width, int height) {}
}