# coding: utf-8
import sys
import os
from pyunpack import Archive

DEFAULT_IN = 'zip_in/'
DEFAULT_OUT = 'zip_out/'


def unpack(f, dir):
    Archive(f).extractall(dir)

if __name__ == "__main__":
    args = sys.argv

    if len(args) < 2:
        in_ = DEFAULT_IN
        out_ = DEFAULT_OUT
    else:
        in_ = args[1]
        out_ = args[2]

    isdir = False

    if not os.path.isdir(out_):
        raise ValueError("[ERROR] The output must be a directory.")

    if os.path.isdir(in_):
        isdir = True
        print("[INFO] Trying to extract all files from %s to %s..." % (in_, out_))
    else:
        print("[INFO] Extracting %s to %s..." % (in_, out_))

    if isdir:
        files = os.listdir(in_)
        for i, f in enumerate(files):
            print("[INFO] Processing %s... [%d/%d]" % (f, i, len(files)))
            try:
                unpack(in_ + f, out_)
            except ValueError as e:
                print("[ERROR] Troubles with file %s, error %s" % (f, e))
    else:
        try:
            unpack(in_, out_)
        except ValueError as e:
            print("[ERROR] Troubles with file %s, error %s" % (in_, e))

    print("[INFO] Done.")