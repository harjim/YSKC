using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterpolicySourceId2SourceId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "policySourceId",
                table: "policySourceUser",
                newName: "sourceId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "sourceId",
                table: "policySourceUser",
                newName: "policySourceId");
        }
    }
}
