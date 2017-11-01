program linear;

const
    MAX = 200000;
    INF = 2000000001;

type
    tarr = array [1..MAX] of longint;

var
    n, m, i, j, k: longint;
    x, v, t: tarr;
    cur, cnt: longint;
    dead: array [1..MAX] of boolean;


begin
    reset(input, 'linear.in');
    rewrite(output, 'linear.out');

    read(n);
    for i := 1 to n do begin
        read(x[i], v[i]);
        x[i] := x[i] * 2;
    end;

    read(m);
    for i := 1 to m do begin
        read(t[i]);
        t[i] := t[i] * 2;
    end;

    cur := 0;
    for i := 1 to m do begin
        while (cur < t[i]) do begin
            for j := 1 to n do begin
                if not dead[j] then
                    x[j] := x[j] + v[j];
            end;
            for j := 1 to n do begin
                if not dead[j] then begin
                    for k := j + 1 to n do begin
                        if not dead[k] then begin
                            if x[j] = x[k] then begin
                                dead[j] := true;
                                dead[k] := true;
                            end;
                        end;
                    end;
                end;
            end;
            inc(cur);
        end;
           
        cnt := 0;
        for j := 1 to n do
            if not dead[j] then
                inc(cnt);
        writeln(cnt);
    end;

    close(input);
    close(output);
end.