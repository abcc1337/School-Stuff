program linear;

const
    MAX = 200000;
    INF = 2000000001;

type
    tarr = array [1..MAX] of longint;

var
    n, m, i, j: longint;
    x, v, t, s: tarr;
    sp: longint;
    cnt: longint;
    die: tarr;


begin
    reset(input, 'linear.in');
    rewrite(output, 'linear.out');

    read(n);
    for i := 1 to n do begin
        read(x[i], v[i]);
        die[i] := INF;
    end;

    read(m);
    for i := 1 to m do begin
        read(t[i]);
        t[i] := t[i] * 2;
    end;

    sp := 0;
    for i := 1 to n do begin
        if (v[i] < 0) and (sp > 0) then begin
            die[i] := x[i] - x[s[sp]];
            die[s[sp]] := die[i];
            sp := sp - 1;
        end;
        if (v[i] > 0) then begin
            sp := sp + 1;
            s[sp] := i;
        end;
    end;

    for i := 1 to m do begin
        cnt := 0;
        for j := 1 to n do begin
            if die[j] > t[i] then
                inc(cnt);
        end;
        writeln(cnt);
    end;

    close(input);
    close(output);
end.