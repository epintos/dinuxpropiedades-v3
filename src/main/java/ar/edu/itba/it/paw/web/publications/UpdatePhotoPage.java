package ar.edu.itba.it.paw.web.publications;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.lang.Bytes;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.photos.Photo;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.utils.ImageResource;
import ar.edu.itba.it.paw.web.utils.WicketUtils;

public class UpdatePhotoPage extends SecuredPage {

	private transient FileUploadField fileUploadField;
	private EntityModel<Publication> publication;

	public UpdatePhotoPage(Publication publication) {
		this.publication = new EntityModel<Publication>(Publication.class,
				publication.getId());
		buildNewPhotoForm();
		buildDeletePhotoForm();
		buildLinks();
	}
	

	private void buildLinks() {
		Link<Void> toPublication = new Link<Void>("toPublication") {
			public void onClick() {
				setResponsePage(new PublicationDetailPage(publication()));

			};
		};
		add(toPublication);

	}

	private void buildNewPhotoForm() {
		Form<UpdatePhotoPage> newPhotoForm = new Form<UpdatePhotoPage>(
				"newPhotoForm") {

			@Override
			protected void onSubmit() {
				Publication pub = publication();
				final List<FileUpload> uploads = fileUploadField
						.getFileUploads();
				List<Photo> photos = new ArrayList<Photo>();
				if (uploads != null) {
					for (FileUpload upload : uploads) {
						photos.add(new Photo(upload.getBytes(), pub));
					}
				}
				pub.addPhotos(photos);
				setResponsePage(new PublicationDetailPage(pub));
			}
		};
		newPhotoForm.setMultiPart(true);
		newPhotoForm.add(fileUploadField = new FileUploadField("file"));
		newPhotoForm.setMaxSize(Bytes.kilobytes(10000));
		newPhotoForm.add(new Button("save", new ResourceModel("save")));
		add(newPhotoForm);
	}

	private void buildDeletePhotoForm() {

		RefreshingView<Photo> rv = new RefreshingView<Photo>("photos") {

			@Override
			protected Iterator<IModel<Photo>> getItemModels() {
				return WicketUtils.getIteratorFor(publication().getPhotos());
			}

			@Override
			protected void populateItem(Item<Photo> item) {
				item.add(new NonCachingImage("photo", new ImageResource(item
						.getModelObject().getFile())));
				item.add(new Link<Photo>("delete", item.getModel()) {
					@Override
					public void onClick() {
						publication().removePhoto(getModelObject());
					}
				});
			}

		};
		add(rv);

		Label empty = new Label("emptyPhotos", Application.get()
				.getResourceSettings().getLocalizer()
				.getString("emptyPhotos", this));
		add(empty);
		if (publication().getPhotos().size() != 0) {
			empty.setVisible(false);
		}
	}

	private Publication publication() {
		return publication.getObject();
	}

	@Override
	public void detachModels() {
		super.detachModels();
		publication.detach();
	}
}
