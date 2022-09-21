using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class alterE_cooperationInfomationToInformation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "infomation",
                table: "e_cooperation",
                newName: "information");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "information",
                table: "e_cooperation",
                newName: "infomation");
        }
    }
}
