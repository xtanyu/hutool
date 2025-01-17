package cn.hutool.core.io;

import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

/**
 * 文件监听单元测试
 *
 * @author Looly
 */
public class WatchMonitorTest {
	WatchMonitor monitor;
	Watcher watcher = new SimpleWatcher() {
		@Override
		public void onCreate(final WatchEvent<?> event, final Path currentPath) {
			final Object obj = event.context();
			Console.log("创建：{}-> {}", currentPath, obj);
			final WatchKey watchKey = monitor.getWatchKey(currentPath);
			final Path watchable = (Path) watchKey.watchable();
			final Path fullPath = watchable.resolve((Path) event.context());
			Console.log("Path 完整对象：{}", fullPath);
		}

		@Override
		public void onModify(final WatchEvent<?> event, final Path currentPath) {
			final Object obj = event.context();
			Console.log("修改：{}-> {}", currentPath, obj);
		}

		@Override
		public void onDelete(final WatchEvent<?> event, final Path currentPath) {
			final Object obj = event.context();
			Console.log("删除：{}-> {}", currentPath, obj);
		}

		@Override
		public void onOverflow(final WatchEvent<?> event, final Path currentPath) {
			final Object obj = event.context();
			Console.log("Overflow：{}-> {}", currentPath, obj);
		}
	};


	@Test
	@Disabled
	public void testFile() {

		monitor = WatchMonitor.createAll("d:/test/aaa.txt", new DelayWatcher(watcher, 500));

		monitor.setMaxDepth(0);
		monitor.start();
	}

	@Test
	@Disabled
	public void testDir() {
		monitor = WatchMonitor.createAll("d:/", new DelayWatcher(watcher, 500));
		monitor.run();
	}

	@Test
	@Disabled
	public void testDelay() {
		monitor = WatchMonitor.createAll("d:/test", new DelayWatcher(new SimpleWatcher(){
			@Override
			public void onModify(final WatchEvent<?> event, final Path currentPath) {
				final Object obj = event.context();
				Console.log("修改：{}-> {}", currentPath, obj);
				ThreadUtil.sleep(5000);
				Console.log("sleep end");
			}

		}, 500));
		monitor.run();
	}
}
