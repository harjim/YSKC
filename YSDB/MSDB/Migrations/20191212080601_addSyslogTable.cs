using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addSyslogTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_sys_logs",
                table: "sys_logs");

            migrationBuilder.RenameTable(
                name: "sys_logs",
                newName: "sys_log");

            migrationBuilder.AddPrimaryKey(
                name: "PK_sys_log",
                table: "sys_log",
                column: "id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_sys_log",
                table: "sys_log");

            migrationBuilder.RenameTable(
                name: "sys_log",
                newName: "sys_logs");

            migrationBuilder.AddPrimaryKey(
                name: "PK_sys_logs",
                table: "sys_logs",
                column: "id");
        }
    }
}
