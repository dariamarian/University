import { Directory, Filesystem } from '@capacitor/filesystem';
import { useCallback } from 'react';

export function useFilesystem() {
  const readFile = useCallback<(path: string) => Promise<string | Blob>>(
    (path) =>
      Filesystem.readFile({
        path,
        directory: Directory.Data,
      }).then(result => result.data), []);

  const writeFile = useCallback<(path: string, data: string) => Promise<any>>(
    (path, data) =>
      Filesystem.writeFile({
        path,
        data,
        directory: Directory.Data,
      }), []);

  const deleteFile = useCallback<(path: string) => Promise<void>>(
    (path) =>
      Filesystem.deleteFile({
        path,
        directory: Directory.Data,
      }), []);

  return {
    readFile,
    writeFile,
    deleteFile,
  };
}
