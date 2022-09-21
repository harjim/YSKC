using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_projectSourceProjectIdDirectionIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "directionId",
                table: "t_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "productId",
                table: "t_project",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "sourceProjectId",
                table: "t_project",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "directionId",
                table: "t_project");

            migrationBuilder.DropColumn(
                name: "productId",
                table: "t_project");

            migrationBuilder.DropColumn(
                name: "sourceProjectId",
                table: "t_project");
        }
    }
}
