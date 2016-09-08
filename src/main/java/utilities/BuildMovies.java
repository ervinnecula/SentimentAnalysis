package utilities;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.BasicContainer;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

public class BuildMovies {

	@Inject
	private static final Logger logger = Logger.getLogger(LoggerProducer.class);

	public static void generateMovie(String outputPath, List<Movie> movies) throws IOException {

		List<Track> videoTracks = new LinkedList<Track>();
		List<Track> audioTracks = new LinkedList<Track>();
		WritableByteChannel fc = null;
		RandomAccessFile raf  = null;
		try {

			for (Movie movieInstance : movies) {
				for (Track track : movieInstance.getTracks()) {
					if (track.getHandler().equals("soun")) {
						audioTracks.add(track);
					}
					if (track.getHandler().equals("vide")) {
						videoTracks.add(track);
					}
				}
			}
			Movie result = new Movie();
			if (audioTracks.size() > 0) {
				result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
			}
			if (videoTracks.size() > 0) {
				result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
			}
			
			Container out = (BasicContainer) new DefaultMp4Builder().build(result);
			raf = new RandomAccessFile(String.format(outputPath), "rw");
			fc = raf.getChannel();
			
			out.writeContainer(fc);
			
		} catch (IOException ex) {
			logger.error(ex);
		} finally {
			raf.close();
			fc.close();
		}
	}

}
