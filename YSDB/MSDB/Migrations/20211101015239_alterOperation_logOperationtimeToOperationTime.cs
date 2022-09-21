using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterOperation_logOperationtimeToOperationTime : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "lastOperationtime",
                table: "operation_log",
                newName: "lastOperationTime");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "lastOperationTime",
                table: "operation_log",
                newName: "lastOperationtime");
        }
    }
}
