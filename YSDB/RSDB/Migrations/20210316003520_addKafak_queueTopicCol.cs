using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addKafak_queueTopicCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "consumed",
                table: "kafka_queue");

            migrationBuilder.AddColumn<byte>(
                name: "status",
                table: "kafka_queue",
                nullable: false,
                defaultValue: (byte)0);

            migrationBuilder.AddColumn<string>(
                name: "topic",
                table: "kafka_queue",
                maxLength: 50,
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "status",
                table: "kafka_queue");

            migrationBuilder.DropColumn(
                name: "topic",
                table: "kafka_queue");

            migrationBuilder.AddColumn<bool>(
                name: "consumed",
                table: "kafka_queue",
                nullable: false,
                defaultValue: false);
        }
    }
}
