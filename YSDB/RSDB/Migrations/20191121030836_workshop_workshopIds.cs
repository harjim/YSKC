using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class workshop_workshopIds : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workshopId",
                table: "p_project");

            migrationBuilder.AddColumn<string>(
                name: "workshopIds",
                table: "p_project",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workshopIds",
                table: "p_project");

            migrationBuilder.AddColumn<int>(
                name: "workshopId",
                table: "p_project",
                nullable: false,
                defaultValue: 0);
        }
    }
}
